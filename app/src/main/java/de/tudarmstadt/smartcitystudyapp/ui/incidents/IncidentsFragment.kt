package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.tudarmstadt.smartcitystudyapp.R

class IncidentsFragment : Fragment() {
    private val incidentsViewModel by viewModels<IncidentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_incidents, container, false)
        val textView: TextView = root.findViewById(R.id.incident_text)
        incidentsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}