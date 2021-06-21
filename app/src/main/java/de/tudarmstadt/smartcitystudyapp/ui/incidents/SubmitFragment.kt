package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.model.SOURCE_OTHER

@AndroidEntryPoint
class SubmitFragment : Fragment() {

    private val submitViewModel: SubmitViewModel by viewModels()
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_submitincidents, container, false)
        val suggestion: String = arguments?.getString("suggestion") ?: ""
        submitViewModel.source = arguments?.getString("source") ?: SOURCE_OTHER
        val galleryButton = root.findViewById<Button>(R.id.incidents_button_gallery)
        val cameraButton = root.findViewById<Button>(R.id.incidents_button_camera)
        val sendPhotoSwitch = root.findViewById<SwitchCompat>(R.id.switch_send_photo)

        root.findViewById<EditText>(R.id.report_text).setText(suggestion)

        root.findViewById<Button>(R.id.incidents_button_submit)
            .setOnClickListener { submitViewModel.sendDummyReport(root) }

        sendPhotoSwitch.setOnClickListener {
            when (sendPhotoSwitch.isChecked) {
                true -> {
                    galleryButton.visibility = View.VISIBLE
                    cameraButton.visibility = View.VISIBLE
                }
                false -> {
                    galleryButton.visibility = View.INVISIBLE
                    cameraButton.visibility = View.INVISIBLE
                }
            }
        }

        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
}