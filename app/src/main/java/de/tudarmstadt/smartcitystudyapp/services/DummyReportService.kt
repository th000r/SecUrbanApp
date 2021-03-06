package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.ReportServiceInterface
import de.tudarmstadt.smartcitystudyapp.models.ReportModel
import kotlinx.coroutines.delay

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

object DummyReportService : ReportServiceInterface {

    override suspend fun postReport(report: ReportModel, imagePaths: MutableList<String>): String {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val reportId = Integer.toHexString(report.hashCode())
        return "Received report $report and saved with the id $reportId."
    }
}