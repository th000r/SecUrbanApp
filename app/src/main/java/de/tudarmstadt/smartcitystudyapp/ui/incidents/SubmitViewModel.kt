package de.tudarmstadt.smartcitystudyapp.ui.incidents

import android.net.Uri
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.models.ReportModel
import de.tudarmstadt.smartcitystudyapp.models.SOURCE_OTHER
import de.tudarmstadt.smartcitystudyapp.interfaces.ReportServiceInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.UserServiceInterface
import de.tudarmstadt.smartcitystudyapp.models.SelectedImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubmitViewModel @ViewModelInject constructor(
    private val reportServiceInterface: ReportServiceInterface,
    private val userServiceInterface: UserServiceInterface,
) : ViewModel() {
    var source: String = SOURCE_OTHER
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var selected_images: MutableList<SelectedImageModel> = mutableListOf()
    private var image_id = 1 // id for created image views (preview image)

    fun sendReport(view: View, finalActionId: Int) {
        val context = view.context

        // extract image paths
        var imageFilePaths: MutableList<String> = mutableListOf<String>()

        for (image in selected_images) {
            image.path?.let { imageFilePaths.add(it) }
        }

        if(MainActivity.networkAvailable) {
            println("Sending report") //TODO: Remove debug reports
            var returnVal = ""

            Toast.makeText(context, R.string.report_prepare_toast, Toast.LENGTH_SHORT).show()

            viewModelScope.launch() {
                Looper.myLooper() ?: Looper.prepare()

                val report = ReportModel(
                    userId = userServiceInterface.getUserId() ?: "???",
                    message = view.findViewById<EditText>(R.id.report_text).text.toString(),
                    picture = view.findViewById<SwitchCompat>(R.id.switch_send_photo).isChecked,
                    location = view.findViewById<SwitchCompat>(R.id.switch_send_location).isChecked,
                    latitude = latitude,
                    longitude = longitude,
                    source = source
                )

                withContext(Dispatchers.IO) {
                    returnVal = reportServiceInterface.postReport(report, imageFilePaths)
                    Log.i("sendReport Result", returnVal)
                }

                withContext(Dispatchers.Main) {
                    if (returnVal.contains("Post failed")) {
                        Toast.makeText(context, R.string.report_sent_error_toast, Toast.LENGTH_LONG).show()
                    } else if (returnVal.contains("Exception")) {
                        Toast.makeText(context, R.string.report_sent_error_toast, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, R.string.report_sent_success_toast, Toast.LENGTH_LONG).show()
                        view.findNavController().navigate(finalActionId)
                    }
                }
            }
        } else {
            Toast.makeText(context, context.getText(R.string.incidents_submit_connectivity_toast), Toast.LENGTH_LONG).show()
        }
    }

    fun addSelectedImage(path: String?, uri: Uri?): Int {
        selected_images.add(SelectedImageModel(image_id, path, uri))
        return image_id++
    }

    fun removeSelectedImage(id: Int) {
        for (i in 0..selected_images.size) {
            if(selected_images.get(i).id == id) {
                selected_images.removeAt(i)
                return
            }
        }
    }

    fun setLocation(lat: Double, long: Double) {
        latitude = lat
        longitude = long
    }
}