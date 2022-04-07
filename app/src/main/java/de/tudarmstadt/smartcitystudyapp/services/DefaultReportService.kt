package de.tudarmstadt.smartcitystudyapp.services

import android.util.Log
import de.tudarmstadt.smartcitystudyapp.interfaces.services.ApiInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.services.ReportService
import de.tudarmstadt.smartcitystudyapp.models.ReportModel
import de.tudarmstadt.smartcitystudyapp.utils.FileUploadUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar.*


const val targetUrl = "http://10.0.2.2:8888"

object DefaultReportService : ReportService {

    override suspend fun postReport(report: ReportModel, imagePaths: MutableList<String>): String {
        val uploadUtil = FileUploadUtil()
        var parts: MutableList<MultipartBody.Part?>? = mutableListOf()
        val postBody = "{" +
                "\"userId\":\"${report.userId}\"," +
                "\"message\":\"${report.message}\"," +
                "\"location\":\"${report.location}\"," +
                "\"latitude\":${report.latitude}," +
                "\"longitude\":${report.longitude}," +
                "\"picture\":\"${report.picture}\"," +
                "\"source\":\"${report.source}\"," +
                "\"timestamp\":\"${currentDateTimeString()}\"" +
                "}"

        for (path in imagePaths) {
            val file = File(path)
            val mimeType = uploadUtil.getMimeType(file)

            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
               // return "Post failed"
            }
            parts?.add(MultipartBody.Part.createFormData(file.name, file.name, file.asRequestBody(
                mimeType?.toMediaTypeOrNull()
            )))
        }


        val response = ApiInterface.create().postReport(
            postBody.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            parts
        )

        if (!response.isSuccessful) {
            Log.d("Exception", response.message().toString())
            return "Post failed"
        } else {
            return "Post successful"
        }
    }
    override suspend fun sendReport(report: ReportModel, imagePaths: MutableList<String>): String {
            val url = "$targetUrl/report"
            val uploadUtil = FileUploadUtil()
            var files: MutableList<File> = mutableListOf()
            var requestBodyBuilder: MultipartBody.Builder? = null
            val jsonMediaType = "application/json; charset=utf-8".toMediaType()
            val client = OkHttpClient()

            val postBody = "{" +
                    "\"userId\":\"${report.userId}\"," +
                    "\"message\":\"${report.message}\"," +
                    "\"location\":\"${report.location}\"," +
                    "\"latitude\":${report.latitude}," +
                    "\"longitude\":${report.longitude}," +
                    "\"picture\":\"${report.picture}\"," +
                    "\"source\":\"${report.source}\"," +
                    "\"timestamp\":\"${currentDateTimeString()}\"" +
                    "}"

            try {
                requestBodyBuilder =
                    MultipartBody.Builder().setType(MultipartBody.FORM)

                for (path in imagePaths) {
                    var file = File(path)
                    files.add(file)
                    val mimeType = uploadUtil.getMimeType(file)

                    if (mimeType == null) {
                        Log.e("file error", "Not able to get mime type")
                        return "Post failed"
                    }

                    requestBodyBuilder
                        .addFormDataPart("uploaded_file", file.name, file.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
                }

                requestBodyBuilder
                    .addFormDataPart("report", "report", postBody.toRequestBody("multipart/form-data".toMediaTypeOrNull()))

                val requestBody = requestBodyBuilder.build()

                val request: Request = Request.Builder().url(url).post(requestBody).build()

                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    Log.d("Exception", response.message.toString())
                    return "Post failed"
                } else {
                    return response.body?.toString() ?: "Response had no body."
                }

            } catch (ex: Exception) {
                Log.d("Exeception", ex.toString())
                return "Exception"
            }
    }

    private fun currentDateTimeString(): String {
        val currentDateTime = getInstance()
        return "${currentDateTime.get(YEAR)}-${currentDateTime.get(MONTH)+1}-${currentDateTime.get(DATE)} " +
                "${currentDateTime.get(HOUR_OF_DAY)}:${currentDateTime.get(MINUTE)}:${currentDateTime.get(SECOND)}"
    }
}