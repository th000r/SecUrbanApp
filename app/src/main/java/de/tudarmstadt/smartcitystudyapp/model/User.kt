package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

enum class StudyGroup {
    GROUP_1,
    GROUP_2
}

@Entity(tableName = "users")
data class User constructor(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "studyGroup") val studyGroup: StudyGroup
)

class Converters {
    @TypeConverter
    fun studyGroupToString(studyGroup: StudyGroup): String {
        return studyGroup.name
    }

    @TypeConverter
    fun stringToStudyGroup(string: String): StudyGroup {
        return StudyGroup.valueOf(string)
    }
}