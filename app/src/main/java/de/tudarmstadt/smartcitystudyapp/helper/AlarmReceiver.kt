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
 * handles the broadcast messages and generates local push notifications
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //Get notification manager to manage/send notifications
        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        // val intentToRepeat = Intent(context, MainActivity::class.java)
        val id = intent.getIntExtra("id", NotificationHelper.ALARM_TYPE_RTC)
        //set flag to restart/relaunch the app
        // intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val intentToRepeat = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("fragment", "activity")
        }
        //Pending intent to handle launch of Activity in intent above
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            id,
//            intentToRepeat,
//            PendingIntent.FLAG_ONE_SHOT
//        )

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
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) as NotificationCompat.Builder
    }
}