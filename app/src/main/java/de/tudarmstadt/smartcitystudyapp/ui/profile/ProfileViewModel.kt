package de.tudarmstadt.smartcitystudyapp.ui.profile

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.model.Report
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val reportService: ReportService
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun sendDummyReport(view: View) {
        println("Sending dummy report")
        viewModelScope.launch(Dispatchers.IO) {
            val report = Report("42069", "my message", location = true, picture = false)
            val response = reportService.sendReport(report)
            _text.postValue(response)
        }
    }
}