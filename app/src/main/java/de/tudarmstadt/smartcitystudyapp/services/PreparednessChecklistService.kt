package de.tudarmstadt.smartcitystudyapp.services

import androidx.annotation.WorkerThread
import de.tudarmstadt.smartcitystudyapp.database.PreparednessChecklistDao
import de.tudarmstadt.smartcitystudyapp.interfaces.PreparednessChecklistServiceInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.UserServiceInterface
import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreparednessChecklistService @Inject constructor(
    private val preparednessChecklistDao: PreparednessChecklistDao
) : PreparednessChecklistServiceInterface {

    override suspend fun getPreparednessChecklistItems(): Flow<List<PreparednessChecklistModel>> {
        return preparednessChecklistDao.loadAll()
    }

    @WorkerThread
    override suspend fun getPreparednessChecklistItem(name: String): Flow<PreparednessChecklistModel?> {
        return preparednessChecklistDao.loadByName(name)
    }

    override suspend fun setPreparednessChecklistItem(item: PreparednessChecklistModel) = withContext(Dispatchers.IO){
        preparednessChecklistDao.save(item)
    }

    override suspend fun updatePreparednessChecklistItem(name: String, done: Boolean, todo: Boolean) = withContext(Dispatchers.IO){
        preparednessChecklistDao.updateToDo(name, done, todo)
    }
}