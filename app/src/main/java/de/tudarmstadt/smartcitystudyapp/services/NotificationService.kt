package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Notification

interface NotificationService {
    suspend fun addNotification(notification: Notification)

    suspend fun loadNotification(notificationId: String): Notification?

    fun getUnreadNotificationCount(): Int

    fun deleteAllNotifications()
}