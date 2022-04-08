package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.ReportModel

interface ReportServiceInterface {
    suspend fun postReport(report: ReportModel, imagePaths: MutableList<String>): String
}