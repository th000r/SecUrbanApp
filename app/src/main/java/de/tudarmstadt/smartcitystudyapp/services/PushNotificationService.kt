package de.tudarmstadt.smartcitystudyapp.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.helper.NotificationHelper
import de.tudarmstadt.smartcitystudyapp.model.NotificationSchedule
import de.tudarmstadt.smartcitystudyapp.model.NotificationStatus
import java.util.*

class PushNotificationService(): Service(){
    private var wakeLock: PowerManager.WakeLock? = null
    companion object {
        var notificationSchedule: MutableList<NotificationSchedule> = ArrayList()
    }

    //ToDo: change to 60 minutes
    private var rescheduleNotificationTime: Long = 1000 * 60 * 1 // ms, sec, min

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

        // schedule push notifications every 60 minutes
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                NotificationHelper.scheduleNextRTCNotification(applicationContext)
                handler.postDelayed(this, rescheduleNotificationTime)
            }
        }, rescheduleNotificationTime)

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
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.logo_inapp)
            .setContentIntent(pendingIntent)
            .build()

        initSchedule()
        // enable service restart on reboot
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
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT)
        applicationContext.getSystemService(Context.ALARM_SERVICE)
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent)
    }

    fun initSchedule() {
        var calendar = Calendar.getInstance()
        notificationSchedule.add(NotificationSchedule(1, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 1, NotificationStatus.DISPLAY, "1", getString(R.string.notification_incidents_subheader)))
        notificationSchedule.add(NotificationSchedule(1, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 2, NotificationStatus.CANCEL))
        notificationSchedule.add(NotificationSchedule(2, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 3, NotificationStatus.DISPLAY, "2", getString(R.string.notification_incidents_subheader)))
        notificationSchedule.add(NotificationSchedule(2, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 4, NotificationStatus.CANCEL))
        notificationSchedule.add(NotificationSchedule(3, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 5, NotificationStatus.DISPLAY, "3", getString(R.string.notification_incidents_subheader)))
        notificationSchedule.add(NotificationSchedule(3, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE) + 6, NotificationStatus.CANCEL))


        /*        notificationSchedule.add(NotificationSchedule(1, Calendar.FRIDAY, 12, 3, NotificationStatus.DISPLAY, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader)))
            notificationSchedule.add(NotificationSchedule(1, Calendar.FRIDAY, 12, 8, NotificationStatus.CANCEL))
            notificationSchedule.add(NotificationSchedule(1, Calendar.SUNDAY, 9, 43, NotificationStatus.DISPLAY, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader)))
            notificationSchedule.add(NotificationSchedule(1, Calendar.SUNDAY, 12, 43, NotificationStatus.CANCEL))
            notificationSchedule.add(NotificationSchedule(2, Calendar.MONDAY, 17, 22, NotificationStatus.DISPLAY, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader)))
            notificationSchedule.add(NotificationSchedule(2, Calendar.MONDAY, 20, 22, NotificationStatus.CANCEL))
            notificationSchedule.add(NotificationSchedule(3, Calendar.THURSDAY, 7, 51, NotificationStatus.DISPLAY, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader)))
            notificationSchedule.add(NotificationSchedule(3, Calendar.THURSDAY, 10, 51, NotificationStatus.CANCEL))
            notificationSchedule.add(NotificationSchedule(4, Calendar.FRIDAY, 21, 11, NotificationStatus.DISPLAY, getString(R.string.notification_incidents_header), getString(R.string.notification_incidents_subheader)))
            notificationSchedule.add(NotificationSchedule(4, Calendar.SATURDAY, 0, 11, NotificationStatus.CANCEL))*/
    }
}