package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private val activitiesViewModel: ActivitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        activitiesViewModel.individualActivities.observe(viewLifecycleOwner, {
            it.forEach{entry -> println(entry.text)} //TODO make the flow->live data->item in ui process work
        })
        root.findViewById<AppCompatImageButton>(R.id.refresh_activities_button).setOnClickListener {
            updateActivities()
        }
        updateActivities()
        return root
    }

    private fun updateActivities() {
        activitiesViewModel.fetchNewIndividualActivities(
            resources.getStringArray(R.array.individual_activities_array).toList()
        )
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