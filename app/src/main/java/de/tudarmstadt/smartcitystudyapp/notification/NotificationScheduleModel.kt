package de.tudarmstadt.smartcitystudyapp.notification

data class NotificationScheduleModel(
    val id: Int,
    val dayOfWeek: Int,
    val hour: Int,
    val min: Int,
    val status: NotificationStatusEnum,
    val title: String? = "",
    val message: String? = ""
)
