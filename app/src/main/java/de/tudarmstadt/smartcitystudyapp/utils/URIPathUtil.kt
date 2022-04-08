package de.tudarmstadt.smartcitystudyapp.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

class URIPathUtil {

    /**
     * Get the string path to a file from the file uri
     * @param context Context
     * @param contentUri URI of the file
     * @return String path of the given file
     */
    fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e("URIPathUtil", "getRealPathFromURI Exception : $e")
            ""
        } finally {
            cursor?.close()
        }
    }
}