package de.tudarmstadt.smartcitystudyapp.ui.welcome

import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.User
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.launch

class IdEntryViewModel @ViewModelInject constructor(
    private val userService: UserService
): ViewModel() {

    private val _warningText = MutableLiveData<String>()
    val warningText: LiveData<String> = _warningText

    fun submitUserId(view: View) {
        viewModelScope.launch {
            val userId = view.findViewById<EditText>(R.id.edit_text_study_id).text.toString()
            userService.getStudyGroup(userId)?.run {
                userService.setUser(User(userId, this))
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context)
                sharedPreferences.edit().putBoolean("show_welcome", false).apply()
                view.context.startActivity(Intent(view.context, MainActivity::class.java))
            } ?: kotlin.run {
                _warningText.postValue(view.context.getString(R.string.invalid_user_id))
            }
        }
    }
}
