package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = arrayOf(ForeignKey(entity = Team::class,
        parentColumns = arrayOf("teamId"),
        childColumns = arrayOf("teamId"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE))
)
data class User constructor(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "points") val points: Int = 0,
    @ColumnInfo(name = "teamId", index = true) val teamId: String
)

