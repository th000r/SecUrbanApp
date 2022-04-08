package de.tudarmstadt.smartcitystudyapp.utils

import android.webkit.MimeTypeMap
import java.io.File

class FileUploadUtil() {

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
}