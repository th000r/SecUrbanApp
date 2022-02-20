package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

val suggestionNavigationSettings = listOf(
    Triple(R.id.incidents_button_water, R.string.water_heading, R.array.water_array),
    Triple(R.id.incidents_button_trees, R.string.trees_heading, R.array.trees_array),
    Triple(R.id.incidents_button_electronic, R.string.electronic_heading, R.array.electronic_array),
    Triple(R.id.incidents_button_vehicles, R.string.vehicles_heading, R.array.vehicles_array),
    Triple(R.id.incidents_button_buildings, R.string.buildings_heading, R.array.buildings_array),
    Triple(R.id.incidents_button_path, R.string.path_heading, R.array.path_array),
    Triple(R.id.incidents_button_devices, R.string.devices_heading, R.array.devices_array),
    Triple(R.id.incidents_button_health, R.string.health_heading, R.array.health_array),
    Triple(R.id.incidents_button_noise, R.string.noise_heading, R.array.noise_array),
    Triple(R.id.incidents_button_air, R.string.air_heading, R.array.air_array),
    Triple(R.id.incidents_button_garbage, R.string.garbage_heading, R.array.garbage_array),
    Triple(R.id.incidents_button_parking, R.string.parking_heading, R.array.parking_array),
    Triple(R.id.incidents_button_smoke, R.string.smoke_heading, R.array.smoke_array),
    Triple(R.id.incidents_button_wc, R.string.wc_heading, R.array.wc_array),
    Triple(R.id.incidents_button_streets, R.string.streets_heading, R.array.streets_array),
    Triple(R.id.incidents_button_animals, R.string.animals_heading, R.array.animals_array),
    Triple(R.id.incidents_button_pollution, R.string.pollution_heading, R.array.pollution_array),
    Triple(R.id.incidents_button_privacy, R.string.privacy_heading, R.array.privacy_array),
    Triple(R.id.incidents_button_security, R.string.security_heading, R.array.security_array),
    Triple(R.id.incidents_button_other, R.string.other_heading, R.array.other_array)
)

@FragmentScoped
class IncidentsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.invalidateOptionsMenu()
        return inflater.inflate(R.layout.fragment_incidents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        suggestionNavigationSettings.forEach { (buttonId, headingId, arrayId) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_incidents_to_suggestion, bundleOf(
                        "headingStringId" to headingId,
                        "categoryStringId" to arrayId
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }
}