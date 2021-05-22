package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitViewModel @ViewModelInject constructor(
    private val reportService: ReportService,
    private val userService: UserService
) :
    ViewModel() {
    fun sendDummyReport(view: View) {
        println("Sending dummy report") //TODO: Remove debug reports
        viewModelScope.launch(Dispatchers.IO) {
            Looper.myLooper() ?: Looper.prepare()
            val preToast = Toast.makeText(view.context, R.string.report_prepare_toast, Toast.LENGTH_SHORT)
            preToast.show()
            val report = Report(
                userService.getUserId(),
                view.findViewById<EditText>(R.id.report_text).text.toString(),
                picture = false,
                latitude = 0.0,
                longitude = 0.0,
                source = ""
            )
            reportService.sendReport(report)
            val postToast = Toast.makeText(view.context, R.string.report_sent_toast, Toast.LENGTH_SHORT)
            postToast.show()
            println("Sent dummy report $report")
        }
    }
}