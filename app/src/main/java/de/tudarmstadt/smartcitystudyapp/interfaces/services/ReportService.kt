package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.Report

interface ReportService {
    suspend fun sendReport(report: Report): String
}