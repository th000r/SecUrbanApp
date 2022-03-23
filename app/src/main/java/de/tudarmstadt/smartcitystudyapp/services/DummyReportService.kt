package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.services.ReportService
import de.tudarmstadt.smartcitystudyapp.models.Report
import kotlinx.coroutines.delay

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

object DummyReportService : ReportService {
    override suspend fun sendReport(report: Report): String {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val reportId = Integer.toHexString(report.hashCode())
        return "Received report $report and saved with the id $reportId."
    }
}