package de.tudarmstadt.smartcitystudyapp.interfaces;

import de.tudarmstadt.smartcitystudyapp.models.ActivityModel
import kotlinx.coroutines.flow.Flow

interface ActivitiesServiceInterface {
    fun getIndividualActivities(): Flow<List<ActivityModel>>
    suspend fun saveNewIndividualActivities(newActivities: List<String>)
}
