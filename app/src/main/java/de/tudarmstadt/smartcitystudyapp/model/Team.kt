package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "teams"
)
data class Team constructor(
    @PrimaryKey @ColumnInfo(name = "teamId") val teamId: String,
    @ColumnInfo(name = "teamName") val teamName: String,
    @ColumnInfo(name = "points") val points: Int = 0
)