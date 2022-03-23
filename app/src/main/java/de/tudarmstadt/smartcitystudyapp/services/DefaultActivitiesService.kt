package de.tudarmstadt.smartcitystudyapp.services;

import androidx.annotation.WorkerThread
import de.tudarmstadt.smartcitystudyapp.database.ActivitiesDao
import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultActivitiesService @Inject constructor(
    private val activitiesDao: ActivitiesDao
) : ActivitiesService {
    override fun getIndividualActivities(): Flow<List<ActivityEntry>> = activitiesDao.loadIndividual()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun saveNewIndividualActivities(newActivities: List<String>) {
        newActivities.forEach{
            activitiesDao.save(ActivityEntry(0, it, 0))
        }
    }
}
