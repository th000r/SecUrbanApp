package de.tudarmstadt.smartcitystudyapp.services;

import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry
import kotlinx.coroutines.flow.Flow

interface ActivitiesService {
    fun getIndividualActivities(): Flow<List<ActivityEntry>>

    suspend fun saveNewIndividualActivities(newActivities: List<String>)
}
