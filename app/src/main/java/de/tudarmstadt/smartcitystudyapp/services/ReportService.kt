package de.tudarmstadt.smartcitystudyapp.services

import android.util.Log
import de.tudarmstadt.smartcitystudyapp.BuildConfig
import de.tudarmstadt.smartcitystudyapp.interfaces.ApiInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.ReportServiceInterface
import de.tudarmstadt.smartcitystudyapp.models.ReportModel
import de.tudarmstadt.smartcitystudyapp.utils.FileUploadUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar.*

object ReportService : ReportServiceInterface {
    private const val MAX_BYTE_UPLOAD_SIZE: Long = 10490000 // max upload size of 10 MB

    /**
     * POST Report and captured images
     * @param report Report Model
     * @param imagePaths Paths to the attached images
     * @return Returns a string containing the success/error message
     */
    override suspend fun postReport(report: ReportModel, imagePaths: MutableList<String>): String {
        val uploadUtil = FileUploadUtil()
        var parts: MutableList<MultipartBody.Part?>? = mutableListOf()
        var latitude = 0.0
        var longitude = 0.0

        // upload real gps data?
        if (BuildConfig.GPS_LOCATION_ACTIVATED) {
            latitude = report.latitude
            longitude = report.longitude
        }

        val postBody = "{" +
                "\"userId\":\"${report.userId}\"," +
                "\"message\":\"${report.message}\"," +
                "\"location\":\"${report.location}\"," +
                "\"latitude\":${latitude}," +
                "\"longitude\":${longitude}," +
                "\"picture\":\"${report.picture}\"," +
                "\"source\":\"${report.source}\"," +
                "\"timestamp\":\"${currentDateTimeString()}\"" +
                "}"

        // upload real images?
        if (BuildConfig.REPORT_IMAGES_ACTIVATED) {
            // get mime type for every image and add them to the request body
            for (path in imagePaths) {
                val file = File(path)
                val mimeType = uploadUtil.getMimeType(file)
                val upload_permitted = uploadUtil.checkUploadSize(file, MAX_BYTE_UPLOAD_SIZE)

                if (!upload_permitted) {
                    return "Exceeded max upload size"
                }

                if (mimeType == null) {
                    Log.e("file error", "Not able to get mime type")
                    return "Post failed"
                }
                parts?.add(
                    MultipartBody.Part.createFormData(
                        file.name, file.name, file.asRequestBody(
                            mimeType.toMediaTypeOrNull()
                        )
                    )
                )
            }
        }

        // add request bodies and send the request
        try {
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
        } catch(e: Exception) {
            Log.d("Exception", e.message.toString())
            return "Post failed"
        }
    }

    /**
     * Returns the current date and time as string
     * @return Returns a string containing the current date and time
     */
    private fun currentDateTimeString(): String {
        val currentDateTime = getInstance()
        return "${currentDateTime.get(YEAR)}-${currentDateTime.get(MONTH)+1}-${currentDateTime.get(DATE)} " +
                "${currentDateTime.get(HOUR_OF_DAY)}:${currentDateTime.get(MINUTE)}:${currentDateTime.get(SECOND)}"
    }
}