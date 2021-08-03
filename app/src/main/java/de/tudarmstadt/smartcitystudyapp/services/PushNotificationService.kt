package de.tudarmstadt.smartcitystudyapp.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R

class PushNotificationService(context: Context) {
    private var mContext = context

    val intent = Intent(mContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        putExtra("fragment", "activity")
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    fun createNotification(channel_id: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(mContext, channel_id)
            .setSmallIcon(R.drawable.logo_inapp)
            .setContentTitle("Neue Meldung")
            .setContentText("Es ist etwas in deiner Umgebung passiert")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}