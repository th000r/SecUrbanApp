package de.tudarmstadt.smartcitystudyapp.interfaces.services;

import de.tudarmstadt.smartcitystudyapp.models.Activity
import kotlinx.coroutines.flow.Flow

interface ActivitiesService {
    fun getIndividualActivities(): Flow<List<Activity>>

    suspend fun saveNewIndividualActivities(newActivities: List<String>)
}
