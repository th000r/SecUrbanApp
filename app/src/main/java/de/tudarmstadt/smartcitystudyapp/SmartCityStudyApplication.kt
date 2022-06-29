package de.tudarmstadt.smartcitystudyapp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.HiltAndroidApp
import org.matomo.sdk.Matomo
import org.matomo.sdk.Tracker
import org.matomo.sdk.TrackerBuilder
import org.matomo.sdk.extra.MatomoApplication
import de.tudarmstadt.smartcitystudyapp.BuildConfig
import de.tudarmstadt.smartcitystudyapp.interfaces.UserServiceInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SmartCityStudyApplication : MatomoApplication() {
    @Inject
    lateinit var userServiceInterface: UserServiceInterface

    private var tracker: Tracker? = null

    override fun onCreateTrackerConfig(): TrackerBuilder {
        return TrackerBuilder.createDefault(BuildConfig.MATOMO_URL, 1)
    }

    @Synchronized
    override fun getTracker(): Tracker? {
        if (tracker == null) {
            tracker = TrackerBuilder.createDefault(BuildConfig.MATOMO_URL, 1)
                .build(Matomo.getInstance(this))

            GlobalScope.launch {
                var userId = userServiceInterface.getUserId()
                tracker?.userId = userId
            }

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