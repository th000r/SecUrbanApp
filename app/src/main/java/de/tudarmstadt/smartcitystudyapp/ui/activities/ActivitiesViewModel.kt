package de.tudarmstadt.smartcitystudyapp.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry
import de.tudarmstadt.smartcitystudyapp.services.ActivitiesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class ActivitiesViewModel @ViewModelInject constructor(
    private val activitiesService: ActivitiesService
) : ViewModel() {
    val individualActivities: LiveData<List<ActivityEntry>> =
        activitiesService.getIndividualActivities().asLiveData()

    fun fetchNewIndividualActivities(strings: List<String>) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            activitiesService.saveNewIndividualActivities(
                List(Random.nextInt(0, 4)) {
                    strings[Random.nextInt(0, strings.size - 1)]
                }
            )
        }
    }
}