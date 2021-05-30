package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.model.SOURCE_OTHER
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitViewModel @ViewModelInject constructor(
    private val reportService: ReportService,
    private val userService: UserService
) : ViewModel() {
    var source: String = SOURCE_OTHER

    fun sendDummyReport(view: View) {
        println("Sending report") //TODO: Remove debug reports
        viewModelScope.launch(Dispatchers.IO) {
            Looper.myLooper() ?: Looper.prepare()
            val context = view.context
            Toast.makeText(context, R.string.report_prepare_toast, Toast.LENGTH_SHORT).show()

            val report = Report(
                userId = userService.getUserId() ?: "???",
                message = view.findViewById<EditText>(R.id.report_text).text.toString(),
                picture = view.findViewById<SwitchCompat>(R.id.switch_send_photo).isChecked,
                location = view.findViewById<SwitchCompat>(R.id.switch_send_location).isChecked,
                source = source
            )
            reportService.sendReport(report)
            Toast.makeText(context, R.string.report_sent_toast, Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_global_home)
            println("Sent report $report")
        }
    }
}