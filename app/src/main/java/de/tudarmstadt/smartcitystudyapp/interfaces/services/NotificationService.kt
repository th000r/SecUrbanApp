package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.NotificationModel

interface NotificationService {
    suspend fun addNotification(notification: NotificationModel)

    suspend fun loadNotification(notificationId: String): NotificationModel?

    fun getUnreadNotificationCount(): Int

    fun deleteAllNotifications()
}