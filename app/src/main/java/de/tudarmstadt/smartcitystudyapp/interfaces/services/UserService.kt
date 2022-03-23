package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun getUserId() : String?

    suspend fun getUser(userId: String): Flow<User?>

    suspend fun getUserID(): Flow<String?>

    suspend fun setUser(user: User)

    suspend fun addPoints(user: User, points: Int)
}