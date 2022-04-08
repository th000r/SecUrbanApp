package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.models.SOURCE_OTHER
import de.tudarmstadt.smartcitystudyapp.models.SelectedImageModel
import de.tudarmstadt.smartcitystudyapp.utils.DimensionsUtil
import de.tudarmstadt.smartcitystudyapp.utils.URIPathUtil
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SubmitFragment : Fragment() {
    private val submitViewModel: SubmitViewModel by viewModels() // viewmodel
    private val REQUEST_IMAGE_CAPTURE = 1000 // camera image capture request code
    private val REQUEST_IMAGE_GALLERY = 1100 // gallery image selection request code
    private val REQUEST_CODE_LOCATION = 2000 // gps location request code
    private val button_active_color = R.color.main_blue // active color for submit button
    private val button_disabled_color = R.color.grey // inactive color for submit button
    private var selected_image_id = 0 // id of currently selected image view
    private lateinit var photoFile: File // placeholder for captured image
    private lateinit var br: BroadcastReceiver
    private lateinit var filter: IntentFilter // filter for current network status
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var imagesPreviewLinearLayout: LinearLayout

    /******************************
    ////////// LIFECYCLE //////////
    ******************************/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** INIT **/
        val root = inflater.inflate(R.layout.fragment_submitincidents, container, false)
        val suggestion: String = arguments?.getString("suggestion") ?: ""
        submitViewModel.source = arguments?.getString("source") ?: SOURCE_OTHER

        // init views
        val galleryButton = root.findViewById<Button>(R.id.incidents_button_gallery)
        val cameraButton = root.findViewById<Button>(R.id.incidents_button_camera)
        val sendPhotoSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_photo)
        val submitButton =  root.findViewById<Button>(R.id.incidents_button_submit)
        val locationSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_location)
        val imagesScrollView = root.findViewById<HorizontalScrollView>(R.id.images_scroll_view)
        imagesPreviewLinearLayout = root.findViewById(R.id.images_preview)

        // init network status
        filter = IntentFilter(getString(R.string.broadcast_network_status)).apply{
            addAction(R.string.broadcast_network_status.toString())
        }

        // init text suggestions
        root.findViewById<EditText>(R.id.report_text).setText(suggestion)

        //init gps location service
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        /** CLICK LISTENER **/
        // submit button
        submitButton.setOnClickListener {
            submitViewModel.sendReport(root, R.id.action_submit_to_thankyou)
        }

        // gps switch
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

        // image upload switch
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

                    mParams = imagesScrollView.layoutParams
                    mParams.apply {
                        height = DimensionsUtil.dpToPx(requireActivity().resources.displayMetrics, 100)
                    }
                    imagesScrollView.layoutParams = mParams
                    imagesScrollView.visibility = View.VISIBLE
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

                    mParams = imagesScrollView.layoutParams
                    mParams.apply {
                        height = 0
                    }
                    imagesScrollView.layoutParams = mParams
                    imagesScrollView.visibility = View.INVISIBLE
                }
            }
        }

        // select image from gallery
        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*"
            startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), REQUEST_IMAGE_GALLERY)
        }

        // select image from camera
        cameraButton.setOnClickListener {
            try {
                photoFile = createImageFile()
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "de.tudarmstadt.smartcitystudyapp.captureimage.fileprovider",
                        photoFile!!
                    )

                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            } catch (e: ActivityNotFoundException) {

            }
        }
        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // listen for network connectivity changes and set the background color of the submit button
        br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i("NETWORK", "received")
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

        // get network status when view is created
        if(MainActivity.networkAvailable) {
            setButtonColor(view, button_active_color)
        } else {
            setButtonColor(view, button_disabled_color)
        }
    }

    override fun onResume() {
        super.onResume()
        // register network listener
        LocalBroadcastManager.getInstance(super.requireContext()).registerReceiver(br!!, filter!!)
    }

    override fun onStop() {
        super.onStop()
        // unregister network listener
        LocalBroadcastManager.getInstance(super.requireContext()).unregisterReceiver(br!!)
    }



    /*********************************
    ////////// CONTEXT MENU //////////
    *********************************/

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = MenuInflater(requireContext())
        inflater.inflate(R.menu.image_upload_context_menu, menu)

        // save id of selected image for further actions
        selected_image_id = v.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // remove image from preview
            R.id.context_menu_delete_image -> {
                imagesPreviewLinearLayout.removeView(imagesPreviewLinearLayout.findViewById(selected_image_id))
                submitViewModel.removeSelectedImage(selected_image_id)
                return true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }



    /************************************
    ////////// ACTIVITY RESULT //////////
     ***********************************/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val myBitmap = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                val selectedImageUri = FileProvider.getUriForFile(
                    requireContext(),
                    "de.tudarmstadt.smartcitystudyapp.captureimage.fileprovider",
                    photoFile!!
                )
                val imageView = ImageView(requireContext())
                imageView.adjustViewBounds = true
                imageView.setImageBitmap(myBitmap)
                imageView.setPadding(DimensionsUtil.dpToPx(requireActivity().resources.displayMetrics, 10), 0, 0, 0)
                imageView.id = submitViewModel.addSelectedImage(photoFile!!.absolutePath, selectedImageUri)
                registerForContextMenu(imageView)
                imagesPreviewLinearLayout.addView(imageView)
            }

            if (requestCode == REQUEST_IMAGE_GALLERY) {
                val imageBitmap = data?.data
                val selectedImageUri = data!!.data!!
                val selectedImagePath = URIPathUtil().getRealPathFromURI(requireContext(), selectedImageUri)
                val imageView = ImageView(requireContext())
                imageView.adjustViewBounds = true
                imageView.setImageURI(imageBitmap)
                imageView.setPadding(5, 0, 0, 0)
                imageView.id = submitViewModel.addSelectedImage(selectedImagePath, selectedImageUri)
                registerForContextMenu(imageView)
                imagesPreviewLinearLayout.addView(imageView)
            }
        }
    }



    /****************************
    ////////// METHODS //////////
     ****************************/

    /**
     * changes the submit button color
     * @param view view that contains the submit button
     * @param color Color resource in R.color
     */
    @Suppress("DEPRECATION")
    fun setButtonColor(view: View, color: Int) {
        var button =  view.findViewById<Button>(R.id.incidents_button_submit)
        var buttonDrawable: Drawable? = button.getBackground()
        buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(resources, color, null))
            button.background = buttonDrawable
        } else {
            button.setBackgroundColor(resources.getColor(color));
            button.invalidate();
        }
    }

    /**
     * Requires GPS permission if none is granted
     * Updates the current gps location (latitude, longitude)
     */
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

    /**
     * Updates the current gps location if permission was just granted
     */
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

    /**
     * Creates an image placeholder file in the app directory to save images taken by the camera
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = ContextCompat.getExternalFilesDirs(requireContext(), Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir[0]      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun getCapturedImagePath(file: File): String {
        val storageDir = ContextCompat.getExternalFilesDirs(requireContext(), Environment.DIRECTORY_PICTURES).toString()
        val fileName = file.name

        return storageDir[0].plus(fileName)
    }
}
