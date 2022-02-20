package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notification constructor(
    @PrimaryKey @ColumnInfo(name = "notificationId") val notificationId: String,
    @ColumnInfo(name = "status") val status: Int
)