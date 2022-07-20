package de.tudarmstadt.smartcitystudyapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoCategory
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoTracker

@FragmentScoped
class HelpSlideFourFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Matomo
        MatomoTracker.setParams(MatomoCategory.HELP, "/help/slideFour")
        MatomoTracker.initFragment()

        return inflater.inflate(R.layout.help_slide_4, container, false)
    }
}