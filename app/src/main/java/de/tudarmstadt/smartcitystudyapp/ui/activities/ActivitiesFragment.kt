package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.models.ActivityModel

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private val activitiesViewModel: ActivitiesViewModel by viewModels()
    private var showAllActivities = false
    private lateinit var adapter: ArrayAdapter<String>
    private val displayLimit = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.invalidateOptionsMenu()
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_list_item_1, emptyList<String>().toMutableList()
        )
        root.findViewById<ListView>(R.id.activities_scroll_view).adapter = adapter
        val button_show_hide_activities = root.findViewById<AppCompatButton>(R.id.button_show_hide_all_activities)

        // load all activites in reversed order (latest on top) and display the latest 3 elements
        displayActivities(displayLimit)

        // refresh button
        root.findViewById<AppCompatImageButton>(R.id.refresh_activities_button).setOnClickListener {
            updateActivities()

            // refresh and display all activites in reversed order
            if (showAllActivities) {
                displayActivities()
            // refresh and display the latest 3 activities in reversed order
            } else {
                displayActivities(displayLimit)
            }
        }

        // show/hide activities button
        button_show_hide_activities.setOnClickListener {
            // show/hide all activities and change the button text
            showAllActivities = !showAllActivities

            if (showAllActivities) {
                displayActivities()
                button_show_hide_activities.text = resources.getText(R.string.incidents_hide_all_button)
            } else {
                displayActivities(displayLimit)
                button_show_hide_activities.text = resources.getText(R.string.incidents_show_all_button)
            }
        }

        updateActivities()
        return root
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    private fun updateActivities() {
        activitiesViewModel.fetchNewIndividualActivities(
            resources.getStringArray(R.array.individual_activities_array).toList()
        )
    }

    private fun displayActivities(limit: Int = -1) {
            activitiesViewModel.individualActivities.observe(viewLifecycleOwner, {
                adapter.clear()

                if (limit <= 0) {
                    adapter.addAll(
                        it.map(
                            ActivityModel::text
                        ).toMutableList().reversed()
                    )
                } else {
                    adapter.addAll(
                        it.map(
                            ActivityModel::text
                        ).toMutableList().reversed().take(limit)
                    )
                }
            })
    }
}