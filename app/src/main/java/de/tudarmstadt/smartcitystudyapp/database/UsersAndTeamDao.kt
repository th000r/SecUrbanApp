package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import de.tudarmstadt.smartcitystudyapp.models.TeamModel
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.models.UserAndTeamModel
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.OnConflictStrategy.IGNORE
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersAndTeamDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(user: UserModel)

    @Insert(onConflict = IGNORE)
    suspend fun save(vararg team: TeamModel)

    /*
    @Transaction
    @Query("SELECT * FROM users")
    suspend fun loadAll(): List<UsersAndTeam>
     */

    @Transaction
    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    fun getByTeamId(teamId: String): Flow<UserAndTeamModel>
}