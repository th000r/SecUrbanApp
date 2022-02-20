package de.tudarmstadt.smartcitystudyapp.services

import androidx.lifecycle.LiveData
import de.tudarmstadt.smartcitystudyapp.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun getUserId() : String?

    suspend fun getUser(userId: String): Flow<User?>

    suspend fun getUserID(): Flow<String?>

    suspend fun setUser(user: User)

    suspend fun addPoints(user: User, points: Int)
}