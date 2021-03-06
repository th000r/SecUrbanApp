package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationModel constructor(
    @PrimaryKey @ColumnInfo(name = "notificationId") val notificationId: String,
    @ColumnInfo(name = "status") val status: Int
)