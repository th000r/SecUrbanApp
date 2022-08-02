package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PreparednessChecklistDao {
    @Insert(onConflict = REPLACE)
    fun save(item: PreparednessChecklistModel)

    @Query("UPDATE preparedness_checklist SET done = :done, todo = :todo WHERE name = :name")
    fun updateToDo(name: String, done: Boolean, todo: Boolean)

    @Query("SELECT * FROM preparedness_checklist WHERE name = :name")
    fun loadByName(name: String): Flow<PreparednessChecklistModel>

    @Query("SELECT * FROM preparedness_checklist")
    fun loadAll(): Flow<List<PreparednessChecklistModel>>
}
