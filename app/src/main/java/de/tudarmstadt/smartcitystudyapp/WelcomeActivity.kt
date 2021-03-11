package de.tudarmstadt.smartcitystudyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.services.UserService
import de.tudarmstadt.smartcitystudyapp.ui.welcome.IdEntryViewModel
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity: AppCompatActivity() {
    @Inject lateinit var userService: UserService
    lateinit var idEntryViewModel: IdEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val showWelcome = sharedPreferences.getBoolean("show_welcome", true)
        if (!showWelcome) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}