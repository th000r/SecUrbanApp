package de.tudarmstadt.smartcitystudyapp

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.model.User
import de.tudarmstadt.smartcitystudyapp.services.UserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity: AppCompatActivity() {
    @Inject lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val showWelcome = sharedPreferences.getBoolean("show_welcome", true)
        if (!showWelcome) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun submitUserId(view: View) {
        val userIdTextField = findViewById<EditText>(R.id.edit_text_study_id)
        GlobalScope.launch {
            val userId = userIdTextField.text.toString()
            userService.getStudyGroup(userId)?.run {
                userService.setUser(User(userId, this))
            }
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.edit().putBoolean("show_welcome", false).apply()
        startActivity(Intent(this, MainActivity::class.java))
    }
}