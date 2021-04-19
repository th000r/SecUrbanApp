package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R

@FragmentScoped
class WaterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listOf<CardView>(
            view.findViewById(R.id.water_one_card),
            view.findViewById(R.id.water_two_card)
        ).forEach { it.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_water_to_submit)) }
    }
}