package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.model.SOURCE_OTHER
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubmitViewModel @ViewModelInject constructor(
    private val reportService: ReportService,
    private val userService: UserService
) : ViewModel() {
    var source: String = SOURCE_OTHER

    fun sendReport(view: View) {
        val context = view.context

        if(MainActivity.networkAvailable) {
            println("Sending report") //TODO: Remove debug reports
            var returnVal = ""

            Toast.makeText(context, R.string.report_prepare_toast, Toast.LENGTH_SHORT).show()

            viewModelScope.launch() {
                Looper.myLooper() ?: Looper.prepare()

                val report = Report(
                    userId = userService.getUserId() ?: "???",
                    message = view.findViewById<EditText>(R.id.report_text).text.toString(),
                    picture = view.findViewById<SwitchCompat>(R.id.switch_send_photo).isChecked,
                    location = view.findViewById<SwitchCompat>(R.id.switch_send_location).isChecked,
                    source = source
                )

                withContext(Dispatchers.IO) {
                    returnVal = reportService.sendReport(report)
                    Log.i("sendReport Result", returnVal)
                }

                withContext(Dispatchers.Main) {
                    if (!returnVal.contains("Post failed with code")) {
                        Toast.makeText(context, R.string.report_sent_success_toast, Toast.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_global_home)
                    } else {
                        Toast.makeText(context, R.string.report_sent_error_toast, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(context, context.getText(R.string.incidents_submit_connectivity_toast), Toast.LENGTH_LONG).show()
        }
    }
}