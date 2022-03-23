package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.Notification

interface NotificationService {
    suspend fun addNotification(notification: Notification)

    suspend fun loadNotification(notificationId: String): Notification?

    fun getUnreadNotificationCount(): Int

    fun deleteAllNotifications()
}