package de.tudarmstadt.smartcitystudyapp.interfaces.services

import android.app.Activity
import android.net.Uri
import de.tudarmstadt.smartcitystudyapp.models.ReportModel
import java.io.File

interface ReportService {
    suspend fun sendReport(report: ReportModel, imagePaths: MutableList<String>): String
    suspend fun postReport(report: ReportModel, imagePaths: MutableList<String>): String
}