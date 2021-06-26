package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Report
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

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
                "\"source\":\"${report.source}\"" +
                "}"
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val request =
            Request.Builder().url(url).post(postBody.toRequestBody(jsonMediaType)).build()

        client.newCall(request).execute().use { response ->
            return if (!response.isSuccessful) {
                "Post failed with code ${response.code}."
            } else {
                response.body?.string() ?: "Response had no body."
            }
        }
    }
}