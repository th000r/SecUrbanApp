package de.tudarmstadt.smartcitystudyapp.helper

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.SystemClock
import android.util.Log
import java.util.*


/**
 * Created by ptyagi on 4/17/17.
 */
object NotificationHelper {
    var ALARM_TYPE_RTC = 100
    private var alarmManagerRTC: AlarmManager? = null
    private var alarmIntentRTC: PendingIntent? = null
    var ALARM_TYPE_ELAPSED = 101
    private var alarmManagerElapsed: AlarmManager? = null
    private var alarmIntentElapsed: PendingIntent? = null

    /**
     * This is the real time /wall clock time
     * @param context
     */
    fun scheduleRepeatingRTCNotification(context: Context, id: Int, dayOfWeek: Int, hour: Int, min: Int, title: String?, message: String?) {
        //get calendar instance to be able to select what time notification should be scheduled
        val calendar = Calendar.getInstance()
        val sharedPref = context.getSharedPreferences("de.tudarmstadt.smartcitystudyapp.shared_key", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sharedPref.edit()
        calendar.timeInMillis = System.currentTimeMillis()
        //Setting time of the day (8am here) when notification will be sent every day (default)
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.DAY_OF_WEEK] = dayOfWeek

        //Setting intent to class where Alarm broadcast message will be handled

        // generate unique id for every notification and every calendar week
        val broadcastId = calendar.get(Calendar.YEAR).toString().plus(calendar.get(Calendar.WEEK_OF_YEAR).toString().plus(id.toString())).toInt()

        //Save id to remember if notification was already displayed
        val savedId = sharedPref.getInt(broadcastId.toString(), 0)
        Log.d("savedID", savedId.toString())
        ed.putInt(broadcastId.toString(), broadcastId)
        ed.apply()

        if (savedId != 0) {
            return
        }
        //Handle intent for push notification
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("title", title)
        intent.putExtra("message", message)
        intent.putExtra("id", broadcastId)
        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(
            context,
            broadcastId,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        //getting instance of AlarmManager service
        alarmManagerRTC = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only
        alarmManagerRTC!!.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntentRTC
        )
    }

    /***
     * This is another way to schedule notifications using the elapsed time.
     * Its based on the relative time since device was booted up.
     * @param context
     */
    fun scheduleRepeatingElapsedNotification(context: Context) {
        //Setting intent to class where notification will be handled
        val intent = Intent(context, AlarmReceiver::class.java)

        //Setting pending intent to respond to broadcast sent by AlarmManager everyday at 8am
        alarmIntentElapsed = PendingIntent.getBroadcast(
            context,
            ALARM_TYPE_ELAPSED,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //getting instance of AlarmManager service
        alarmManagerElapsed = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Inexact alarm everyday since device is booted up. This is a better choice and
        //scales well when device time settings/locale is changed
        //We're setting alarm to fire notification after 15 minutes, and every 15 minutes there on
        alarmManagerElapsed!!.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntentElapsed
        )
    }

    fun cancelAlarmRTC() {
        if (alarmManagerRTC != null) {
            alarmManagerRTC!!.cancel(alarmIntentRTC)
        }
    }

    fun cancelAlarmElapsed() {
        if (alarmManagerElapsed != null) {
            alarmManagerElapsed!!.cancel(alarmIntentElapsed)
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
}