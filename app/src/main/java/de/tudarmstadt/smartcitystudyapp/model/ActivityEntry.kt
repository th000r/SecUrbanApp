package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntry constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val text: String,
    val type: Int //0 for individual or 1 for team
)
