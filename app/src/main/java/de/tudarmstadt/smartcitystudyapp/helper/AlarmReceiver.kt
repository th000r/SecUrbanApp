package de.tudarmstadt.smartcitystudyapp.helper

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R

/**
 * Local Push Notification Helper
 * handles the broadcast messages and generates local push notifications
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", NotificationHelper.ALARM_TYPE_RTC)
        val intentToRepeat = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("fragment", "activity")
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, id, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)

        //Build notification
        val repeatedNotification: Notification =
            buildLocalNotification(context, pendingIntent, intent).build()

        //Send local notification
        NotificationHelper.getNotificationManager(context)
            .notify(id, repeatedNotification)
    }

    fun buildLocalNotification(
        context: Context?,
        pendingIntent: PendingIntent?,
        intent: Intent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context!!, "de.tudarmstadt.smartcitystudyapp.channel.REPORT")
            .setSmallIcon(R.drawable.logo_inapp)
            .setContentTitle(intent?.getStringExtra("title"))
            .setContentText(intent?.getStringExtra("message"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) as NotificationCompat.Builder
    }
}