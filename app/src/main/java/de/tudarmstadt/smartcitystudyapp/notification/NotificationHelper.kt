package de.tudarmstadt.smartcitystudyapp.notification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import de.tudarmstadt.smartcitystudyapp.notification.NotificationStatusEnum.*
import java.util.*

/**
 * Local Push Notification Helper
 * Schedules Push Notifications
 */
object NotificationHelper {

    var ALARM_TYPE_RTC = 100
    private var alarmManagerRTC: AlarmManager? = null
    private var alarmIntentRTC: PendingIntent? = null

    /**
     * Schedules weekly push notifications at a specific day and time
     * @param context
     * @param id unique id to identify the push notification
     * @param dayOfWeek scheduled day of the week
     * @param hour scheduled hour of the day
     * @param min scheduled minutes of the day
     * @param title push notification title
     * @param message push notification message
     */
    fun scheduleRTCNotification(context: Context, id: Int, dayOfWeek: Int, hour: Int, min: Int, title: String?, message: String?) {
        //get calendar instance to be able to select what time notification should be scheduled
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        //setting the time and day of the week for the push notification to be displayed
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.DAY_OF_WEEK] = dayOfWeek

        //Handle intent for push notification
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("title", title)
        intent.putExtra("message", message)
        intent.putExtra("uid", id)
        intent.putExtra("action", "display")
        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        alarmManagerRTC = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only
        alarmManagerRTC!!.set(
            AlarmManager.RTC,
            calendar.timeInMillis, alarmIntentRTC
        )


    }

    fun cancelNotification(context: Context, id: Int, dayOfWeek: Int, hour: Int, min: Int) {
        //get calendar instance to be able to select what time notification should be scheduled
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        //setting the time and day of the week for the push notification to be displayed

        // generate broadcast id of notification to cancel
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.DAY_OF_WEEK] = dayOfWeek

        //Handle intent for push notification
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("action", "cancel")
        intent.putExtra("uid", id)

        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        alarmManagerRTC = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManagerRTC!!.set(
            AlarmManager.RTC,
            calendar.timeInMillis, alarmIntentRTC
        )

        Log.d("Cancel Scheduled", "Now")
    }

    fun cancelAlarmRTC() {
        if (alarmManagerRTC != null) {
            alarmManagerRTC!!.cancel(alarmIntentRTC)
        }
    }

    fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * Enable boot receiver to persist alarms set for notifications across device reboots
     */
    fun enableBootReceiver(context: Context) {
        val receiver = ComponentName(context, AlarmBootReceiver::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    /**
     * Disable boot receiver when user cancels/opt-out from notifications
     */
    fun disableBootReceiver(context: Context) {
        val receiver = ComponentName(context, AlarmBootReceiver::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    /**
     * Schedule the next notification for this week
     * Important: Scheduled items should be in chronological order
     */
    fun scheduleNextRTCNotification(context: Context) {
        val calendar = Calendar.getInstance()

        for (schedule in PushNotificationService.notificationSchedule) {
            Log.d("Schedule all", schedule.id.toString() + " " + schedule.status.toString())

            Log.d(calendar.get(Calendar.DAY_OF_WEEK).toString() + " == " + schedule.dayOfWeek.toString(), ((calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE)).toString() + " <= " +  ((schedule.hour * 60) + schedule.min).toString())
            // execute the next scheduled notification
            if (calendar.get(Calendar.DAY_OF_WEEK) == schedule.dayOfWeek && (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE) <= (schedule.hour * 60) + schedule.min) {
                when (schedule.status) {
                    DISPLAY -> {
                        Log.d("Schedule display", schedule.id.toString() + " " + schedule.status.toString())
                        scheduleRTCNotification(context, schedule.id, schedule.dayOfWeek, schedule.hour, schedule.min, schedule.title, schedule.message)
                        return
                    }
                    CANCEL -> {
                        Log.d("Schedule cancel", schedule.id.toString() + " " + schedule.status.toString())
                        cancelNotification(context, schedule.id, schedule.dayOfWeek, schedule.hour, schedule.min)
                        return
                    }
                }
            }
        }
    }
}