package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.tudarmstadt.smartcitystudyapp.services.StudyGroup

@Entity(tableName = "users")
data class User constructor(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "studyGroup") val studyGroup: StudyGroup
)