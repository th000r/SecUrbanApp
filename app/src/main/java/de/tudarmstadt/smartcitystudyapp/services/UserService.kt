package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.User

enum class StudyGroup {
    GROUP_1,
    GROUP_2
}

interface UserService {
    suspend fun getUserId() : String?

    suspend fun setUser(user: User)

    suspend fun getStudyGroup(): StudyGroup?

    suspend fun getStudyGroup(userId: String): StudyGroup?
}