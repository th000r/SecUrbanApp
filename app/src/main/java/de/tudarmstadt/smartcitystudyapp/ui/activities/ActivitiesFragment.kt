package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private val activitiesViewModel: ActivitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        val adapter = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_list_item_1, emptyList<String>().toMutableList()
        )
        root.findViewById<ListView>(R.id.activities_scroll_view).adapter = adapter

        activitiesViewModel.individualActivities.observe(viewLifecycleOwner, {
            adapter.clear()
            adapter.addAll(
                it.map(
                    ActivityEntry::text
                ).toMutableList()
            )
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
}