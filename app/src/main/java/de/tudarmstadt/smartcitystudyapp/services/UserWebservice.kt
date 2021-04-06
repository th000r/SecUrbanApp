package de.tudarmstadt.smartcitystudyapp.services

interface UserWebservice {
    suspend fun registerUser(userId: String)
}