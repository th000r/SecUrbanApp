package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Report
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

const val targetIp = "127.0.0.1" //set to proper value for local testing
// TODO make configurable for proper release/put actual backend deployment in

object DefaultReportService : ReportService {
    override suspend fun sendReport(report: Report): String {
        val client = OkHttpClient()
        val url = "http://$targetIp:8080/report"
        val postBody = "{" +
                "\"citizenId\":\"${report.userId}\"," +
                "\"message\":\"${report.message}\"," +
                "\"locationData\":\"${report.location}\"," +
                "\"picture\":\"${report.picture}\"" +
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