package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User constructor(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "points") val points: Int = 0
)