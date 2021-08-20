package de.tudarmstadt.smartcitystudyapp.helper

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.services.PushNotificationService

/**
 * Local Push Notification Helper
 * handles the broadcast messages and generates local push notifications
 */
class AlarmReceiver() : BroadcastReceiver() {
    private val NOTIFICATION_ID = 9876
    private val NOTIFICATION_STATUS_ACTIVE = 1
    private val NOTIFICATION_STATUS_INACTIVE = -1
    private val NOTIFICATION_KEY_STATUS = "notification_report_status"
    private val NOTIFICATION_KEY_ID = "notification_report_id"

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPref = context.getSharedPreferences("de.tudarmstadt.smartcitystudyapp", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sharedPref.edit()
        val action = intent.getStringExtra("action")
        val broadcastId = intent.getIntExtra("id", NotificationHelper.ALARM_TYPE_RTC)
        val id = intent.getIntExtra("uid", -1)
        val intentToRepeat = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("fragment", "activity")
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, broadcastId, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)

        //Build notification
        val repeatedNotification: Notification =
            buildLocalNotification(context, pendingIntent, intent).build()

        when(action) {
            "display" -> {
                if (SharedPref.getNotificationId(context.applicationContext) != id) {
                    SharedPref.putNotificationId(context.applicationContext, id)
                    SharedPref.putNotificationStatus(context.applicationContext, NOTIFICATION_STATUS_ACTIVE)
                    NotificationHelper.getNotificationManager(context)
                        .notify(NOTIFICATION_ID, repeatedNotification)
                    }
            }
            "cancel" -> {
                    SharedPref.putNotificationStatus(context.applicationContext, NOTIFICATION_STATUS_INACTIVE)
                Log.d("Cancel","Notification")
                    NotificationHelper.getNotificationManager(context)
                        .cancelAll()
            }
        }
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