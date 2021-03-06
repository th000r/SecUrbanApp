package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.models.NotificationModel

@Dao
interface NotificationDao {
    @Insert(onConflict = REPLACE)
    fun save(notification: NotificationModel)

    @Query("SELECT * FROM notifications WHERE notificationId = :notificationId")
    fun load(notificationId: String): NotificationModel?

    @Query("SELECT COUNT(notificationId) FROM notifications WHERE status = 1")
    fun loadUnreadNotificationCount(): Int

    @Query("DELETE FROM notifications")
    fun deleteAll()
}
