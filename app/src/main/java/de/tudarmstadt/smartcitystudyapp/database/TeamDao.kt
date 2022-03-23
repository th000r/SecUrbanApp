package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.models.Team

@Dao
interface TeamDao {
    @Insert(onConflict = REPLACE)
    fun save(team: Team)

    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    fun load(teamId: String): Team?

    @Query("SELECT * FROM teams")
    fun loadAll(): List<Team>
}
