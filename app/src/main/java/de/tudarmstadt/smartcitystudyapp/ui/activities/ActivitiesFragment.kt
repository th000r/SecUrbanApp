package de.tudarmstadt.smartcitystudyapp.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.tudarmstadt.smartcitystudyapp.R

class ActivitiesFragment : Fragment() {

    private lateinit var activitiesViewModel: ActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activitiesViewModel =
            ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        activitiesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}