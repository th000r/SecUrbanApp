package de.tudarmstadt.smartcitystudyapp.services;

import androidx.annotation.WorkerThread
import de.tudarmstadt.smartcitystudyapp.database.ActivitiesDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.ActivitiesService
import de.tudarmstadt.smartcitystudyapp.models.Activity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultActivitiesService @Inject constructor(
    private val activitiesDao: ActivitiesDao
) : ActivitiesService {
    override fun getIndividualActivities(): Flow<List<Activity>> = activitiesDao.loadIndividual()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun saveNewIndividualActivities(newActivities: List<String>) {
        newActivities.forEach{
            activitiesDao.save(Activity(0, it, 0))
        }
    }
}
