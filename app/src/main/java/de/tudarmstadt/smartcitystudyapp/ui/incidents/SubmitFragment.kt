package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
class SubmitFragment @Inject constructor(
    private val reportService: ReportService
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_submitincidents, container, false)
        val suggestion: String = arguments?.getString("suggestion") ?: ""
        root.findViewById<EditText>(R.id.report_text).setText(suggestion)
        root.findViewById<Button>(R.id.incidents_button_submit)
            .setOnClickListener { sendDummyReport(it) }
        return root
    }

    private fun sendDummyReport(view: View) {
        println("Sending dummy report")
        lifecycleScope.launch(Dispatchers.IO) {
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