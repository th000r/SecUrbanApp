package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

@FragmentScoped
class IncidentsFragment : Fragment() {
    private val incidentsViewModel by viewModels<IncidentsViewModel>()

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
    }
}