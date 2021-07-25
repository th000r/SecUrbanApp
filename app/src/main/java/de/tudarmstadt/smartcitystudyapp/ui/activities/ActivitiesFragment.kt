package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
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
            activitiesViewModel.individualActivities,
            this.context
        )
        return root
    }

    private fun buildIndividualActivitiesList(view: LinearLayoutCompat, entries: List<String>, context: Context?) {
        repeat(entries.size - view.childCount) {
            view.addView(TextView(context).apply {
                text = entries[it]
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                    .height = ActionBar.LayoutParams.WRAP_CONTENT
//                layout.width = ActionBar.LayoutParams.WRAP_CONTENT
            }, 0)
        }
    }
}