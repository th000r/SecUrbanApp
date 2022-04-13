package de.tudarmstadt.smartcitystudyapp.utils

import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File

class FileUploadUtil() {
    private var current_byte_size: Long = 0
    /**
     * Returns the mime type of a file
     * @param file
     * @return Mime Type as String
     */
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun checkUploadSize(file: File, max_byte_size: Long): Boolean {
        val byte_size = file.length()
        Log.d("FILE_SIZE", byte_size.toString())
        current_byte_size += byte_size

        if (current_byte_size <= max_byte_size) {
            return true
        }

        return false
    }
}