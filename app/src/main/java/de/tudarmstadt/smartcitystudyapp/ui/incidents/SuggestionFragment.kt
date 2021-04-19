package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

@FragmentScoped
class SuggestionFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)
        val headingStringId: Int = arguments?.getInt("headingStringId") ?: R.string.water_heading
        val categoryArrayId = arguments?.getInt("categoryStringId") ?: R.array.water_array
        val headingView = root.findViewById<TextView>(R.id.heading)
        val suggestionsLayout = root.findViewById<LinearLayout>(R.id.suggestions_layout)

        headingView.text = getString(headingStringId)
        resources.getStringArray(categoryArrayId).forEach { suggestion -> println(suggestion)
//            val cardView = context?.let { it -> CardView(it).apply {
//
//            } }
//
//            suggestionsLayout.addView(cardView)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        view.findViewById<Button>(R.id.incidents_button_water).setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_to_incident_water)
//        )
    }
}