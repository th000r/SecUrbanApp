package de.tudarmstadt.smartcitystudyapp.interfaces

interface UserWebserviceInterface {
    suspend fun registerUser(userId: String)
}