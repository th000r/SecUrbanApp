package de.tudarmstadt.smartcitystudyapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlin.collections.LinkedHashMap

import de.tudarmstadt.smartcitystudyapp.R

class HelpFragment : Fragment() {

    private val helpViewModel by viewModels<HelpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_help, container, false)
        //val textView: TextView = root.findViewById(R.id.text_help)
        //helpViewModel.text.observe(viewLifecycleOwner, {
        //   textView.text = it
        //})

        return root
    }

}