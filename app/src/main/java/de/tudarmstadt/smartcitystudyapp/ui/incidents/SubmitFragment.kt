package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

@FragmentScoped
class SubmitFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_submitincidents, container, false)
        val suggestion: String = arguments?.getString("suggestion") ?: ""
        root.findViewById<EditText>(R.id.report_text).setText(suggestion)
        return root
    }
}