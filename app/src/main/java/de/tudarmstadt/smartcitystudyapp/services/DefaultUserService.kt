package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.UserDao
import de.tudarmstadt.smartcitystudyapp.model.User
import javax.inject.Inject

class DefaultUserService @Inject constructor(
    private val webservice: UserWebservice,
    private val userDao: UserDao
) : UserService {
    override suspend fun getUserId(): String? {
        userDao.loadAll().let {
            return if (it.isEmpty()) {
                null
            } else {
                return it.first().userId
            }
        }
    }

    override suspend fun setUser(user: User) {
        userDao.save(user)
    }

    override suspend fun getStudyGroup(): StudyGroup? {
        getUserId().let {
            return if (it == null) {
                null
            } else {
                userDao.load(it)?.studyGroup
            }
        }
    }

    override suspend fun getStudyGroup(userId: String): StudyGroup? {
        userDao.load(userId).let {
            return it?.studyGroup
                ?: webservice.getStudyGroup(userId).apply {
                    if (this != null) {
                        userDao.save(User(userId, this))
                    }
                }
        }
    }
}