package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.models.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: Activity)

    @Query("SELECT * FROM activities WHERE type = 0")
    fun loadIndividual(): Flow<List<Activity>>

    @Query("SELECT * FROM activities WHERE type = 1")
    fun loadTeam(): Flow<List<Activity>>
}