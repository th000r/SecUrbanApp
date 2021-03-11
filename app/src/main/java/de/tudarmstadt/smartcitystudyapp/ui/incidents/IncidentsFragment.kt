package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.tudarmstadt.smartcitystudyapp.R

class IncidentsFragment : Fragment() {

    private lateinit var incidentsViewModel: IncidentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        incidentsViewModel =
            ViewModelProvider(this).get(IncidentsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_incidents, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        incidentsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}