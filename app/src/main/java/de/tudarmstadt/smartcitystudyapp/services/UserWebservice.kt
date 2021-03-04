package de.tudarmstadt.smartcitystudyapp.services

interface UserWebservice {
    suspend fun userIsValid(userId: String): Boolean

    suspend fun getStudyGroup(userId: String): StudyGroup?
}