package de.tudarmstadt.smartcitystudyapp.model

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalTime

data class NotificationSchedule(
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val time: LocalTime = LocalTime.now(),
    val title: String = "",
    val message: String = ""
): Comparable<NotificationSchedule> {
    override fun compareTo(other: NotificationSchedule): Int =
        this.time.compareTo(other.time)
}

