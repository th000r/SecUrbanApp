package de.tudarmstadt.smartcitystudyapp.ui.preparedness

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.adapter.PreparednessChecklistAdapter
import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel
import de.tudarmstadt.smartcitystudyapp.ui.welcome.WelcomeActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FragmentScoped
class PreparednessChecklistFragment : Fragment() {
    private val preparednessChecklistViewModel by viewModels<PreparednessChecklistViewModel>()
    var prepardnessChecklistItems: ArrayList<PreparednessChecklistModel> = ArrayList()
    var adapter: PreparednessChecklistAdapter? = null

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preparedness_checklist, container, false)
        val recyclerview = root.findViewById<RecyclerView>(R.id.preparedness_checklist_recycler_view)

        adapter = PreparednessChecklistAdapter(listOf<PreparednessChecklistModel>(), onClickListener())

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)

        observeItems()

        return root
    }

    inner class onClickListener : PreparednessChecklistAdapter.onItemClickListener {
        override fun onItemClick(model: PreparednessChecklistModel?, doneChecked: Boolean, toDoChecked: Boolean) {
           this@PreparednessChecklistFragment.updateItem(model!!.name, doneChecked, toDoChecked)
        }
    }

    /**
     * Get the list of all items from the database
     */
    @InternalCoroutinesApi
    private fun observeItems() {
        lifecycleScope.launch {
            preparednessChecklistViewModel.preparednessChecklistServiceInterface.getPreparednessChecklistItems().collect { items ->
                if (items.isNotEmpty()) {
                    adapter!!.setSuggestionsList(items)
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * Update changed item in the database
     */
    private fun updateItem(name: String, done: Boolean, todo: Boolean) {
        lifecycleScope.launch {
            preparednessChecklistViewModel.preparednessChecklistServiceInterface.updatePreparednessChecklistItem(name, done, todo)
        }
    }
}