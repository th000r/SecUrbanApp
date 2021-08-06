package de.tudarmstadt.smartcitystudyapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.NotificationSchedule
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.random.Random

class NotificationScheduler(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @SuppressLint("NewApi")
    var notificationSchedules: Array<NotificationSchedule> =
        arrayOf(
            NotificationSchedule(DayOfWeek.FRIDAY, LocalTime.parse("10:35:00"), "test1", "message1"),
            NotificationSchedule(DayOfWeek.FRIDAY, LocalTime.parse("10:20:00"),"test2", "message2"),
            NotificationSchedule(DayOfWeek.FRIDAY, LocalTime.parse("10:50:00"),"test3", "message3"),
            NotificationSchedule(DayOfWeek.FRIDAY, LocalTime.parse("11:05:00"),"test4", "message4")
        )
    @SuppressLint("NewApi")
    override fun doWork(): Result {
//        val timeOfLastUsage = LocalTime
//            .parse(inputData.getString("ReportData"))
        val now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
        var notificationTitle = ""
        var notificationText = ""

        notificationSchedules.sort()
        notificationSchedules = notificationSchedules.reversedArray()

        for (schedule in notificationSchedules) {
            Log.d("datetime", schedule.time.toString())
            Log.d("now", now.toLocalTime().toString())
            // important: schedules have to be sorted by time to get the right item
            if ((now.dayOfWeek == schedule.dayOfWeek) and (now.toLocalTime().isAfter(schedule.time))) {
                notificationTitle = schedule.title
                notificationText = schedule.message

                val builder = NotificationCompat.Builder(
                    context,
                    "de.tudarmstadt.smartcitystudyapp.channel.REPORT"
                )
                    .setSmallIcon(R.drawable.logo_inapp)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(context)) {
                    notify(Random.nextInt(), builder.build())
                }
                break
            }
        }
        return Result.success()
    }
}