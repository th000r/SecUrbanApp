package de.tudarmstadt.smartcitystudyapp.ui.site_notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SiteNoticeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is site notice Fragment"
    }
    val text: LiveData<String> = _text
}