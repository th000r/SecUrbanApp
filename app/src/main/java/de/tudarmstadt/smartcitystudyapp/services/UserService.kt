package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.User

interface UserService {
    suspend fun getUserId() : String?

    suspend fun setUser(user: User)

    suspend fun addPoints(user: User, points: Int)
}