package de.tudarmstadt.smartcitystudyapp.interfaces.services;

import de.tudarmstadt.smartcitystudyapp.models.ActivityModel
import kotlinx.coroutines.flow.Flow

interface ActivitiesService {
    fun getIndividualActivities(): Flow<List<ActivityModel>>

    suspend fun saveNewIndividualActivities(newActivities: List<String>)
}
