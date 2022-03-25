package de.tudarmstadt.smartcitystudyapp.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.database.AppDatabase
import de.tudarmstadt.smartcitystudyapp.interfaces.services.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Local Push Notification Helper
 * handles the broadcast messages and generates local push notifications
 */
class AlarmReceiver() : BroadcastReceiver() {
    @Inject
    lateinit var notificationService: NotificationService
    private val NOTIFICATION_ID = 9876

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra("action")
        val broadcastId = intent.getIntExtra("id", NotificationHelper.ALARM_TYPE_RTC)
        val id = intent.getIntExtra("uid", -1)
        val intentToRepeat = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            when(action) {
                "display" -> putExtra("pushNotification", "display")
                "cancel" -> putExtra("pushNotification", "cancel")
            }

        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, broadcastId, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)

        //Build notification
        val repeatedNotification: Notification =
            buildLocalNotification(context, pendingIntent, intent).build()

        when(action) {
            "display" -> {
                Log.d("Display Notification","Notification " + id)

                CoroutineScope(Dispatchers.IO).launch {
                    updateNotificationStatus(context, id.toString(), 1)
                }

                NotificationHelper.getNotificationManager(context)
                        .notify(NOTIFICATION_ID, repeatedNotification)

            }
            "cancel" -> {
                Log.d("Cancel Notification","Notification " + id)

                CoroutineScope(Dispatchers.IO).launch {
                    updateNotificationStatus(context, id.toString(), -1)
                }

                NotificationHelper.getNotificationManager(context)
                    .cancelAll()
            }
        }
    }

    suspend fun updateNotificationStatus(context: Context, id: String, status: Int) {
        AppDatabase.getDatabase(context).notificationDao().save(de.tudarmstadt.smartcitystudyapp.models.NotificationModel(id.toString(), status))
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