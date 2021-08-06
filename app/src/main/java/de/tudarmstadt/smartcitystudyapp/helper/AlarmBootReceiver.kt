package de.tudarmstadt.smartcitystudyapp.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import de.tudarmstadt.smartcitystudyapp.helper.NotificationHelper.scheduleRepeatingElapsedNotification


/**
 * Created by ptyagi on 4/18/17.
 */
class AlarmBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            //only enabling one type of notifications for demo purposes
            scheduleRepeatingElapsedNotification(context)
        }
    }
}