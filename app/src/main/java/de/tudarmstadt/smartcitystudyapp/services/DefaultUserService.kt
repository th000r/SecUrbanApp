package de.tudarmstadt.smartcitystudyapp.services

import androidx.annotation.WorkerThread
import de.tudarmstadt.smartcitystudyapp.database.UserDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UserService
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UserWebservice
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultUserService @Inject constructor(
    private val webservice: UserWebservice,
    private val userDao: UserDao
) : UserService {
    override suspend fun getUserId(): String? = withContext(Dispatchers.IO){
        userDao.loadAll().let {
            if (it.isEmpty()) {
                null
            } else {
                it.first().userId
            }
        }
    }

    @WorkerThread
    override suspend fun getUserID(): Flow<String?> {
        return userDao.getUserId()
    }

    @WorkerThread
    override suspend fun getUser(userId: String): Flow<UserModel?> {
        return userDao.load(userId)
    }

    override suspend fun setUser(user: UserModel) = withContext(Dispatchers.IO){
        userDao.save(user)
    }

    override suspend fun addPoints(user: UserModel, points: Int) {
        userDao.save(UserModel(user.userId, user.userName, user.city, user.points+points, user.teamId))
    }
}