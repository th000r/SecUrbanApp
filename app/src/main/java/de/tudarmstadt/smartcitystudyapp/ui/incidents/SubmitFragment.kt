package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R

@AndroidEntryPoint
class SubmitFragment : Fragment() {

    private val submitViewModel: SubmitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_submitincidents, container, false)
        val suggestion: String = arguments?.getString("suggestion") ?: ""
        root.findViewById<EditText>(R.id.report_text).setText(suggestion)
        root.findViewById<Button>(R.id.incidents_button_submit)
            .setOnClickListener { submitViewModel.sendDummyReport(it) }
        return root
    }
}