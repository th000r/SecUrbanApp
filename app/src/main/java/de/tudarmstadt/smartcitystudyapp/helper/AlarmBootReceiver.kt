package de.tudarmstadt.smartcitystudyapp.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import de.tudarmstadt.smartcitystudyapp.services.PushNotificationService

/**
 * Local Push Notification Helper
 * Starts the foreground service if the device was restarted
 */
class AlarmBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Intent(context, PushNotificationService::class.java).also {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(it)
                } else {
                    context.startService(it)
                }
            }
        }
    }
}