package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.tudarmstadt.smartcitystudyapp.model.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun load(userId: String): User?

    @Query("SELECT * FROM users")
    fun loadAll(): List<User>
}
