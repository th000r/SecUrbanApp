package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoCategory
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoTracker

@AndroidEntryPoint
class SubmitNotificationFragment : Fragment() {
    private val submitViewModel: SubmitViewModel by viewModels()
    private val REQUEST_IMAGE_CAPTURE = 1000
    private val REQUEST_CODE_LOCATION = 2000
    private val button_active_color = R.color.main_blue
    private val button_disabled_color = R.color.grey
    private var br: BroadcastReceiver? = null
    private var filter: IntentFilter? = null
    private lateinit  var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_submitincidents_notification, container, false)
        val suggestion = ""
        submitViewModel.source = "Notification"
        val galleryButton = root.findViewById<Button>(R.id.incidents_button_gallery)
        val cameraButton = root.findViewById<Button>(R.id.incidents_button_camera)
        val sendPhotoSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_photo)
        val nothingToReportSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_nothing)
        var submitButton = root.findViewById<Button>(R.id.incidents_button_submit)
        val locationSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_location)


        val incidentDescriptionElements = listOf(
            root.findViewById<TextView>(R.id.text_location),
            root.findViewById<SwitchCompat>(R.id.switch_send_location),
            root.findViewById<ImageView>(R.id.image_location),
            root.findViewById<TextView>(R.id.text_description),
            root.findViewById<TextInputLayout>(R.id.text_input_layout),
            root.findViewById<LinearLayout>(R.id.photo_button_group),
            root.findViewById(R.id.horizontal_line_view),
            sendPhotoSwitch
        )

        //Matomo
        MatomoTracker.setParams(MatomoCategory.INCIDENTS, "/incidents/submitNotification")
        MatomoTracker.initFragment()

        filter = IntentFilter(getString(R.string.broadcast_network_status)).apply {
            addAction(R.string.broadcast_network_status.toString())
        }

        root.findViewById<EditText>(R.id.report_text).setText(suggestion)

        submitButton.setOnClickListener {
            submitViewModel.sendReport(root, R.id.action_submit_notification_to_thankyou)
        }

        nothingToReportSwitch.setOnClickListener {
            when (nothingToReportSwitch.isChecked) {
                true -> incidentDescriptionElements.forEach {
                    it.visibility = View.INVISIBLE
                }
                false -> incidentDescriptionElements.forEach {
                    it.visibility = View.VISIBLE
                }
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationSwitch.setOnClickListener {
            when (locationSwitch.isChecked) {
                true -> {
                    updateLocation()
                }
                false -> {
                    submitViewModel.setLocation(0.0, 0.0)
                }
            }
        }

        sendPhotoSwitch.setOnClickListener {
            when (sendPhotoSwitch.isChecked) {
                true -> {
                    var mParams = galleryButton.layoutParams
                    mParams.apply {
                        height = LinearLayout.LayoutParams.WRAP_CONTENT
                    }
                    galleryButton.layoutParams = mParams
                    galleryButton.visibility = View.VISIBLE

                    mParams = cameraButton.layoutParams
                    mParams.apply {
                        height = LinearLayout.LayoutParams.WRAP_CONTENT
                    }
                    cameraButton.layoutParams = mParams
                    cameraButton.visibility = View.VISIBLE
                }
                false -> {
                    var mParams = galleryButton.layoutParams
                    mParams.apply {
                        height = 0
                    }
                    galleryButton.layoutParams = mParams
                    galleryButton.visibility = View.INVISIBLE

                    mParams = cameraButton.layoutParams
                    mParams.apply {
                        height = 0
                    }
                    cameraButton.layoutParams = mParams
                    cameraButton.visibility = View.INVISIBLE
                }
            }
        }

        galleryButton.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*"
            startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), 0)
        }

        cameraButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {

            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // listen for network connectivity changes and set the background color of the submit button
        br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if (intent.hasExtra("status")) {
                        if (getString(R.string.broadcast_network_status).equals(intent.action)) {
                            when (MainActivity.networkAvailable) {
                                true -> {
                                    setButtonColor(view, button_active_color)
                                }
                                false -> {
                                    setButtonColor(view, button_disabled_color)
                                }
                            }
                        }
                    }
                }
            }
        }

        // hacky approach to get the network status when the view is created
        /* ToDo: is there another possibility to get the network status on creation?
        *  Because the network manager broadcasts the status only on network status changes
        */
        if (MainActivity.networkAvailable) {
            setButtonColor(view, button_active_color)
        } else {
            setButtonColor(view, button_disabled_color)
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(super.requireContext()).registerReceiver(br!!, filter!!)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(super.requireContext()).unregisterReceiver(br!!)
    }

    @Suppress("DEPRECATION")
    fun setButtonColor(view: View, color: Int) {
        var button = view.findViewById<Button>(R.id.incidents_button_submit)
        var buttonDrawable: Drawable? = button.getBackground()
        buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(resources, color, null))
            button.background = buttonDrawable
        } else {
            button.setBackgroundColor(resources.getColor(color))
            button.invalidate()
        }
    }

    fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_LOCATION
            )
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    submitViewModel.setLocation(location.latitude, location.longitude)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
                        if (location != null) {
                            submitViewModel.setLocation(location.latitude, location.longitude)
                        }
                    }
                }
            }
        }
    }
}