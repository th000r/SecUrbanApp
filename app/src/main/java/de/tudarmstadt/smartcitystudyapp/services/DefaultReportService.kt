package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.services.ReportService
import de.tudarmstadt.smartcitystudyapp.models.Report
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Calendar.*

const val targetUrl = "smartercity.tk.informatik.tu-darmstadt.de"

object DefaultReportService : ReportService {
    override suspend fun sendReport(report: Report): String {
        val client = OkHttpClient()
        val url = "http://$targetUrl:8080/report"
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
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val request =
            Request.Builder().url(url).post(postBody.toRequestBody(jsonMediaType)).build()

        try {
            client.newCall(request).execute().use { response ->
                return if (!response.isSuccessful) {
                    "Post failed with code ${response.code}."
                } else {
                    response.body?.string() ?: "Response had no body."
                }
            }
        } catch (ex: Exception) {
            return "exception"
        }
    }

    private fun currentDateTimeString(): String {
        val currentDateTime = getInstance()
        return "${currentDateTime.get(YEAR)}-${currentDateTime.get(MONTH)+1}-${currentDateTime.get(DATE)} " +
                "${currentDateTime.get(HOUR_OF_DAY)}:${currentDateTime.get(MINUTE)}:${currentDateTime.get(SECOND)}"
    }
}