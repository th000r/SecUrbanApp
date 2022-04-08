package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserServiceInterface {
    suspend fun getUserId() : String?

    suspend fun getUser(userId: String): Flow<UserModel?>

    suspend fun getUserID(): Flow<String?>

    suspend fun setUser(user: UserModel)

    suspend fun addPoints(user: UserModel, points: Int)
}