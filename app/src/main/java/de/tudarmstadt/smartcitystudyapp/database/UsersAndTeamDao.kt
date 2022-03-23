package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import de.tudarmstadt.smartcitystudyapp.models.Team
import de.tudarmstadt.smartcitystudyapp.models.User
import de.tudarmstadt.smartcitystudyapp.models.UsersAndTeam
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.OnConflictStrategy.IGNORE
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersAndTeamDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(user: User)

    @Insert(onConflict = IGNORE)
    suspend fun save(vararg team: Team)

    /*
    @Transaction
    @Query("SELECT * FROM users")
    suspend fun loadAll(): List<UsersAndTeam>
     */

    @Transaction
    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    fun getByTeamId(teamId: String): Flow<UsersAndTeam>
}