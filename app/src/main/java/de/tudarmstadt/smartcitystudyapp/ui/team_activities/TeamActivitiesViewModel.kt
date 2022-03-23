package de.tudarmstadt.smartcitystudyapp.ui.activities

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.models.Activity
import de.tudarmstadt.smartcitystudyapp.interfaces.services.ActivitiesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TeamActivitiesViewModel @ViewModelInject constructor(
    private val activitiesService: ActivitiesService
) : ViewModel() {
    val individualActivities: LiveData<List<Activity>> =
        activitiesService.getIndividualActivities().asLiveData()

    fun fetchNewIndividualActivities(strings: List<String>) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            activitiesService.saveNewIndividualActivities(
                List(Random.nextInt(0, 2)) {
                    strings[Random.nextInt(0, strings.size - 1)]
                }
            )
        }
    }
}