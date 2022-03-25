package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: UserModel)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun load(userId: String): Flow<UserModel>

    @Query("SELECT * FROM users")
    fun loadAll(): List<UserModel>

    @Query("SELECT userId FROM users LIMIT 1")
    fun getUserId(): Flow<String>
}
