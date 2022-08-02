package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preparedness_checklist")
data class PreparednessChecklistModel constructor(
    @PrimaryKey @ColumnInfo(name = "preparednessChecklistId") val preparednessChecklistId: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "done") val done: Boolean = false,
    @ColumnInfo(name = "todo") val todo: Boolean = false,
)
