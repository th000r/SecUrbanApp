package de.tudarmstadt.smartcitystudyapp.services

interface UserWebservice {
    suspend fun userIsValid(userId: String): Boolean
}