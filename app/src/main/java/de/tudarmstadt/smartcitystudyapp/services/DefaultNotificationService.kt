package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.NotificationDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.NotificationService
import de.tudarmstadt.smartcitystudyapp.models.NotificationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultNotificationService @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationService {
    override fun getUnreadNotificationCount(): Int = notificationDao.loadUnreadNotificationCount()

    override suspend fun addNotification(notification: NotificationModel) = withContext(Dispatchers.IO){
        notificationDao.save(notification)
    }

    override suspend fun loadNotification(notificationId: String) = withContext(Dispatchers.IO){
        notificationDao.load(notificationId)
    }

    override fun deleteAllNotifications() = notificationDao.deleteAll()
}