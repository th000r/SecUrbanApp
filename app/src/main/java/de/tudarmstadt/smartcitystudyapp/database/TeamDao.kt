package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.models.TeamModel

@Dao
interface TeamDao {
    @Insert(onConflict = REPLACE)
    fun save(team: TeamModel)

    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    fun load(teamId: String): TeamModel?

    @Query("SELECT * FROM teams")
    fun loadAll(): List<TeamModel>
}
