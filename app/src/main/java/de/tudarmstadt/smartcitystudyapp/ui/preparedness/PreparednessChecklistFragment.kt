package de.tudarmstadt.smartcitystudyapp.ui.preparedness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.adapter.PreparednessChecklistAdapter
import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel
import de.tudarmstadt.smartcitystudyapp.ui.incidents.suggestionNavigationSettings

@FragmentScoped
class PreparednessChecklistFragment : Fragment() {

    var prepardnessChecklistItems: ArrayList<PreparednessChecklistModel> = ArrayList()
    var adapter: PreparednessChecklistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preparedness_checklist, container, false)
        /*
        val headingStringId: Int = arguments?.getInt("pr") ?: R.string.water_heading
        val categoryArrayId = arguments?.getInt("categoryStringId") ?: R.array.water_array
         */
        val checkListName = root.findViewById<TextView>(R.id.preparedness_checklist_name)
        val checkBoxDone = root.findViewById<CheckBox>(R.id.preparedness_checklist_done)
        val checkBoxToDo = root.findViewById<CheckBox>(R.id.preparedness_checklist_todo)
        val recyclerview = root.findViewById<RecyclerView>(R.id.preparedness_checklist_recycler_view)

        initPreparednessChecklistItems(root)

        adapter = PreparednessChecklistAdapter(
            prepardnessChecklistItems
        )

        recyclerview.adapter = adapter

        recyclerview.layoutManager = LinearLayoutManager(activity)

        return root
    }

    private fun initPreparednessChecklistItems(root: View) {
        val itemNames: Array<String> = root.resources.getStringArray(R.array.preparedness_checklist_item_names)

        //ToDo: get checkbox values from sql db
        for (itemName in itemNames) {
            prepardnessChecklistItems.add(PreparednessChecklistModel(0, itemName,false, false))
        }
    }
}