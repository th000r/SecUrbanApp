package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.NotificationModel

interface NotificationServiceInterface {
    suspend fun addNotification(notification: NotificationModel)
    suspend fun loadNotification(notificationId: String): NotificationModel?
    fun getUnreadNotificationCount(): Int
    fun deleteAllNotifications()
}