package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preparedness_checklist")
data class PreparednessChecklistModel constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "preparednessChecklistId") val preparednessChecklistId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "done") val done: Boolean,
    @ColumnInfo(name = "todo") val todo: Boolean,
)
