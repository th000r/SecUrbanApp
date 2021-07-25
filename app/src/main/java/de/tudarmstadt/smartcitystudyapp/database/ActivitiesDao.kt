package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: ActivityEntry)

    @Query("SELECT * FROM activities WHERE type = 0")
    fun loadIndividual(): Flow<List<ActivityEntry>>

    @Query("SELECT * FROM activities WHERE type = 1")
    fun loadTeam(): Flow<List<ActivityEntry>>
}