package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel
import kotlinx.coroutines.flow.Flow

interface PreparednessChecklistServiceInterface {
    suspend fun getPreparednessChecklistItem(name: String): Flow<PreparednessChecklistModel?>
    suspend fun setPreparednessChecklistItem(item: PreparednessChecklistModel)
    suspend fun updatePreparednessChecklistItem(name: String, done: Boolean, todo: Boolean)
    suspend fun getPreparednessChecklistItems(): Flow<List<PreparednessChecklistModel>>
}