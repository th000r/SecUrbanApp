package de.tudarmstadt.smartcitystudyapp.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R

@AndroidEntryPoint
class UserIdEntryFragment : Fragment() {

    private val idEntryViewModel by viewModels<IdEntryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_id_entry, container, false)
        val warningTextField: TextView = root.findViewById(R.id.invalid_user_id_warning_text_field)
        val enterIdButton: Button = root.findViewById(R.id.button_enter_study_id)
        enterIdButton.setOnClickListener { idEntryViewModel.submitUserId(root) }
        idEntryViewModel.warningText.observe(viewLifecycleOwner, {
            warningTextField.text = it
        })
        root.findViewById<ImageButton>(R.id.button_back_user_id_entry).setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_user_id_back) }
        return root
    }
}