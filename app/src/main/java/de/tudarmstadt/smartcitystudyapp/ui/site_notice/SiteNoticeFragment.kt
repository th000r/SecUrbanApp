package de.tudarmstadt.smartcitystudyapp.ui.site_notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoCategory
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoTracker

@AndroidEntryPoint
class SiteNoticeFragment : Fragment() {

    private val siteNoticeViewModel by viewModels<SiteNoticeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.invalidateOptionsMenu()
        val root = inflater.inflate(R.layout.fragment_site_notice, container, false)

        //Matomo
        MatomoTracker.setParams(MatomoCategory.SITE_NOTICE, "/site-notice")
        MatomoTracker.initFragment()

        // val textView: TextView = root.findViewById(R.id.text_site_notice)
        // siteNoticeViewModel.text.observe(viewLifecycleOwner, Observer {
         //   textView.text = it
        //})
        return root
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }
}