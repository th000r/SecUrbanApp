package de.tudarmstadt.smartcitystudyapp.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.helper.NotificationHelper
import java.util.*

class PushNotificationService(): Service(){
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    /**
     * Not needed
     */
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * Code to be executed if the service was started
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotificationService()

        return START_STICKY
    }

    private fun startNotificationService() {
        // register notification channels to receive push notifications
        registerChannel(getString(R.string.channel_name_report), getString(R.string.channel_description_report), getString(R.string.channel_id_report))
        registerChannel(getString(R.string.channel_name_fgs_report), getString(R.string.channel_description_fgs_report), getString(R.string.channel_id_fgs_report))

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        // notification for the sticky foreground service
        val notification = NotificationCompat.Builder(this, getString(R.string.channel_id_fgs_report))
            .setContentTitle(getString(R.string.notification_incidents_fgs_header))
            .setContentText(getString(R.string.notification_incidents_fgs_subheader))
            .setSmallIcon(R.drawable.logo_inapp)
            .setContentIntent(pendingIntent)
            .build()

        // notification schedule
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 1, Calendar.SUNDAY, 9, 43, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader))
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 2, Calendar.MONDAY, 17, 22,getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader))
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 3, Calendar.THURSDAY,7, 51,getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader))
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 4, Calendar.FRIDAY,21, 11,getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader))

        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 5, Calendar.WEDNESDAY,11, 0,"Neue Meldung!", "11:00")
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 6, Calendar.WEDNESDAY,12, 0,"Neue Meldung!", "12:00")
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 7, Calendar.WEDNESDAY,12, 5,"Neue Meldung!", "12:05")
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 8, Calendar.WEDNESDAY,14, 30,"Neue Meldung!", "14:30")
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 9, Calendar.WEDNESDAY,18, 0,"Neue Meldung!", "18:00")
        NotificationHelper.scheduleWeeklyRTCNotification(applicationContext, 10, Calendar.THURSDAY,0, 30,"Neue Meldung!", "00:30")

        NotificationHelper.enableBootReceiver(applicationContext)

        startForeground(9999, notification)

        // aquire wake lock to prevent the service from being stopped by doze mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }
    }

    /**
     * Registers a notification channel
     * @param channelName channel name
     * @param channelDescription provide a description of your channel's purpose
     * @param channelId unique channel id
     */
    private fun registerChannel(channelName: String, channelDescription: String, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val descriptionText = channelDescription
            val channel_id = channelId
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Restart the foreground service if it was removed by the system
     * otherwise push notifications will not be displayed if the app was killed
     */
    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, PushNotificationService::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        applicationContext.getSystemService(Context.ALARM_SERVICE);
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
    }
}