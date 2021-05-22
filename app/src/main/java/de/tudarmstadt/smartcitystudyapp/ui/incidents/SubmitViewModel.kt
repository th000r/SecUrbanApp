package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.view.View
import android.widget.EditText
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitViewModel @ViewModelInject constructor(private val reportService: ReportService) :
    ViewModel() {
    fun sendDummyReport(view: View) {
        println("Sending dummy report")
        val reportTextField = view.findViewById<EditText>(R.id.report_text)
        viewModelScope.launch(Dispatchers.IO) {
            val report = Report(
                "42069",
                reportTextField.text.toString(),
                picture = false,
                latitude = 0.0,
                longitude = 0.0,
                fromNotification = false
            )
            reportService.sendReport(report)
            println("Sent dummy report $report")
        }
    }
}