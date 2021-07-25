package de.tudarmstadt.smartcitystudyapp.ui.activities

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ActivitiesViewModel : ViewModel() {
    val individualActivities = MutableList(0) { "" }

    fun fetchNewIndividualActivities(strings: List<String>) {
        repeat(Random.nextInt(0, 4)) {
            individualActivities.add(0, strings[Random.nextInt(0, strings.size-1)])
        }
    }
}