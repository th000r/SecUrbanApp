package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.adapter.SuggestionsAdapter


@FragmentScoped
class SuggestionFragment : Fragment() {

    var adapter: SuggestionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_suggestion, container, false)
        val headingStringId: Int = arguments?.getInt("headingStringId") ?: R.string.water_heading
        val categoryArrayId = arguments?.getInt("categoryStringId") ?: R.array.water_array
        val headingView = root.findViewById<TextView>(R.id.suggestion_heading)
        val recyclerview = root.findViewById<RecyclerView>(R.id.recycler_view)

        adapter = SuggestionsAdapter(
            resources.getStringArray(categoryArrayId).toMutableList()
        )
        recyclerview.adapter = adapter

        recyclerview.layoutManager = LinearLayoutManager(activity)

        headingView.text = getString(headingStringId)

        return root
    }
}