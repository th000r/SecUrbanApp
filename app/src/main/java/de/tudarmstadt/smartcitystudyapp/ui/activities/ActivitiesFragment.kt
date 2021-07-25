package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.tudarmstadt.smartcitystudyapp.R

class ActivitiesFragment : Fragment() {

    private val activitiesViewModel by viewModels<ActivitiesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        activitiesViewModel.fetchNewIndividualActivities(
            resources.getStringArray(R.array.individual_activities_array).toList()
        )
        buildIndividualActivitiesList(
            root.findViewById(R.id.activities_scroll_view),
            activitiesViewModel.individualActivities
        )
        return root
    }

    private fun buildIndividualActivitiesList(view: View, entries: List<String>) {
        //TODO: for each string in entries create new textView with the string and add to view
    }
}