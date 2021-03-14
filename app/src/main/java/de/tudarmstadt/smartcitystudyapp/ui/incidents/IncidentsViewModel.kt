package de.tudarmstadt.smartcitystudyapp.ui.incidents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IncidentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Incidents Fragment"
    }
    val text: LiveData<String> = _text
}