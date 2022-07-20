package de.tudarmstadt.smartcitystudyapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dagger.hilt.android.scopes.FragmentScoped

import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoCategory
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoTracker

@FragmentScoped
class HelpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.invalidateOptionsMenu()

        //Matomo
        MatomoTracker.setParams(MatomoCategory.HELP, "/help")
        MatomoTracker.initFragment()

        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.help_button_1).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_to_help_slide_1)
        )
        view.findViewById<Button>(R.id.help_button_2).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_to_help_slide_2)
        )
        view.findViewById<Button>(R.id.help_button_3).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_to_help_slide_3)
        )
        view.findViewById<Button>(R.id.help_button_4).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_to_help_slide_4)
        )
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

}