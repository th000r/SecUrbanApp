package de.tudarmstadt.smartcitystudyapp.interfaces.services

interface UserWebservice {
    suspend fun registerUser(userId: String)
}