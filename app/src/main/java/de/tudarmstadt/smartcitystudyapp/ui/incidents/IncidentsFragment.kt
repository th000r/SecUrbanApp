package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

@FragmentScoped
class IncidentsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_incidents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.incidents_button_water).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_to_incident_water)
        )
        view.findViewById<Button>(R.id.incidents_button_trees).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.trees_heading,
                    "categoryStringId" to R.array.trees_array
                )
            )
        }
    }
}