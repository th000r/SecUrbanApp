package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SubmitViewModel @ViewModelInject constructor(private val reportService: ReportService) :
    ViewModel() {
    fun sendDummyReport(view: View) {
        println("Sending dummy report")
        viewModelScope.launch(Dispatchers.IO) {
            val report = Report(
                "42069",
                "my message",
                picture = false,
                latitude = 0.0,
                longitude = 0.0,
                fromNotification = false
            )
            reportService.sendReport(report)
        }
    }
}