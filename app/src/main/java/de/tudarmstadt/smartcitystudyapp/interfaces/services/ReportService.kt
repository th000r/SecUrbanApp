package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.ReportModel

interface ReportService {
    suspend fun sendReport(report: ReportModel): String
}