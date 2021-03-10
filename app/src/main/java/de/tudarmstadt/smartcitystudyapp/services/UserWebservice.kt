package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.StudyGroup

interface UserWebservice {
    suspend fun userIsValid(userId: String): Boolean

    suspend fun getStudyGroup(userId: String): StudyGroup?
}