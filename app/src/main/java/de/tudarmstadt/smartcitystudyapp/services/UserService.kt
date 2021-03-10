package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.StudyGroup
import de.tudarmstadt.smartcitystudyapp.model.User

interface UserService {
    suspend fun getUserId() : String?

    suspend fun setUser(user: User)

    suspend fun getStudyGroup(): StudyGroup?

    suspend fun getStudyGroup(userId: String): StudyGroup?
}