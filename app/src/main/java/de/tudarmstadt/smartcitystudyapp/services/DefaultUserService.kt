package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.UserDao
import de.tudarmstadt.smartcitystudyapp.model.User
import kotlinx.coroutines.Dispatchers
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

    override suspend fun setUser(user: User) = withContext(Dispatchers.IO){
        userDao.save(user)
    }
}