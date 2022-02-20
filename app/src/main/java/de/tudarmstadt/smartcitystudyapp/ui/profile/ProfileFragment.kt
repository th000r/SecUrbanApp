package de.tudarmstadt.smartcitystudyapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        activity?.invalidateOptionsMenu()

        profileViewModel.getUserId()

        profileViewModel.userId.observe(viewLifecycleOwner, { id: String ->
            if (id != null) {
                profileViewModel.getUser(id)
            }
        })

        profileViewModel.user.observe(viewLifecycleOwner, { user: User ->
            if (user != null) {
                root.findViewById<TextView>(R.id.vp_name).setText(user.userName)
                root.findViewById<TextView>(R.id.vp_city).setText(user.city)
            }
        })


        return root
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }
}