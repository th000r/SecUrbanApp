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
        view.findViewById<Button>(R.id.incidents_button_water).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.water_heading,
                    "categoryStringId" to R.array.water_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_trees).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.trees_heading,
                    "categoryStringId" to R.array.trees_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_electronic).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.electronic_heading,
                    "categoryStringId" to R.array.electronic_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_vehicles).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.vehicles_heading,
                    "categoryStringId" to R.array.vehicles_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_buildings).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.buildings_heading,
                    "categoryStringId" to R.array.buildings_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_path).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.path_heading,
                    "categoryStringId" to R.array.path_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_devices).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.devices_heading,
                    "categoryStringId" to R.array.devices_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_health).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.health_heading,
                    "categoryStringId" to R.array.health_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_noise).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.noise_heading,
                    "categoryStringId" to R.array.noise_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_air).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.air_heading,
                    "categoryStringId" to R.array.air_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_garbage).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.garbage_heading,
                    "categoryStringId" to R.array.garbage_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_parking).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.parking_heading,
                    "categoryStringId" to R.array.parking_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_smoke).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.smoke_heading,
                    "categoryStringId" to R.array.smoke_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_wc).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.wc_heading,
                    "categoryStringId" to R.array.wc_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_streets).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.streets_heading,
                    "categoryStringId" to R.array.streets_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_animals).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.animals_heading,
                    "categoryStringId" to R.array.animals_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_pollution).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.pollution_heading,
                    "categoryStringId" to R.array.pollution_array
                )
            )
        }
        view.findViewById<Button>(R.id.incidents_button_other).setOnClickListener {
            view.findNavController().navigate(
                R.id.action_incidents_to_suggestion,
                bundleOf(
                    "headingStringId" to R.string.other_heading,
                    "categoryStringId" to R.array.other_array
                )
            )
        }
    }
}