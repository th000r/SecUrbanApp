package de.tudarmstadt.smartcitystudyapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import org.matomo.sdk.Matomo
import org.matomo.sdk.Tracker
import org.matomo.sdk.TrackerBuilder
import org.matomo.sdk.extra.MatomoApplication


@HiltAndroidApp
class SmartCityStudyApplication : MatomoApplication() {
    private var tracker: Tracker? = null

    override fun onCreateTrackerConfig(): TrackerBuilder {
        return TrackerBuilder.createDefault("http://10.0.2.2:80/matomo/matomo.php", 1)
    }

    @Synchronized
    override fun getTracker(): Tracker? {
        if (tracker == null) {
            tracker = TrackerBuilder.createDefault("http://10.0.2.2:80/matomo/matomo.php", 1)
                .build(Matomo.getInstance(this))
        }
        return tracker
    }

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