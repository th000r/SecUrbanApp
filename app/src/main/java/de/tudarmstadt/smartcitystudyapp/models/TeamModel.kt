package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "teams"
)
data class TeamModel constructor(
    @PrimaryKey @ColumnInfo(name = "teamId") val teamId: String,
    @ColumnInfo(name = "teamName") val teamName: String,
    @ColumnInfo(name = "points") val points: Int = 0
)