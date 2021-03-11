package de.tudarmstadt.smartcitystudyapp.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import de.tudarmstadt.smartcitystudyapp.R

class ExplanationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_explanation, container, false)
        root.findViewById<ImageButton>(R.id.button_forward_explanation).setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_explanation_forward)
        }
        return root
    }
}