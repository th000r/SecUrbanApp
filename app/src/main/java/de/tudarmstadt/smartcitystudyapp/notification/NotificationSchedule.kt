package de.tudarmstadt.smartcitystudyapp.notification

enum class NotificationStatus {
    DISPLAY,
    CANCEL
}

data class NotificationSchedule(
    val id: Int,
    val dayOfWeek: Int,
    val hour: Int,
    val min: Int,
    val status: NotificationStatus,
    val title: String? = "",
    val message: String? = ""
)
