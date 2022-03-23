package de.tudarmstadt.smartcitystudyapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartCityStudyApplication : Application() {
    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}