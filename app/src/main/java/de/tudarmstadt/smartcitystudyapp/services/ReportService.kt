package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Report

interface ReportService {
    suspend fun sendReport(report: Report): String
}