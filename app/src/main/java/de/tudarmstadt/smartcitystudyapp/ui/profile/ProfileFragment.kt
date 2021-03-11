package de.tudarmstadt.smartcitystudyapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_profile)
        val testButton: Button = root.findViewById(R.id.test_button)
        testButton.setOnClickListener { profileViewModel.sendDummyReport(it) }
        profileViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}