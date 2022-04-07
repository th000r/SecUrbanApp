package de.tudarmstadt.smartcitystudyapp.utils

import android.app.ProgressDialog
import android.net.Uri
import android.os.FileUtils
import android.webkit.MimeTypeMap
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Part
import java.io.File


//class FileUploadUtil(activity: Activity) {
class FileUploadUtil() {

    //var activity = activity;
    var dialog: ProgressDialog? = null
    var serverURL: String = "http://10.0.2.2:8888/reportImageUpload"
    var serverUploadDirectoryPath: String = "/upload/report"
    val client = OkHttpClient()

    /*
    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }

    fun uploadFile(sourceFileUri: Uri, uploadedFileName: String? = null) {
        val pathFromUri = URIPathUtil().getRealPathFromURI(activity,sourceFileUri)
        uploadFile(File(pathFromUri), uploadedFileName)
    }

    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile);
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = if (uploadedFileName == null)  sourceFile.name else uploadedFileName
            // toggleProgressDialog(true)
            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload","success, path: $serverUploadDirectoryPath$fileName")
                    showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
            // toggleProgressDialog(false)
        }.start()
    }

    fun uploadFiles(sourceFilePaths: MutableList<String>, uploadedFileNames: MutableList<String> = mutableListOf()) {
        var files: MutableList<File> = mutableListOf()

        for (path in sourceFilePaths) {
            files.add(File(path))
        }

        uploadFiles(files, uploadedFileNames)
    }

    @JvmName("uploadFiles1")
    fun uploadFiles(sourceFiles: MutableList<File>, uploadedFileNames: MutableList<String> = mutableListOf()) {
        Thread {

            var requestBodyBuilder: MultipartBody.Builder? = null
            // toggleProgressDialog(true)
            try {
                requestBodyBuilder =
                    MultipartBody.Builder().setType(MultipartBody.FORM)

                for (file in sourceFiles) {
                    val mimeType = getMimeType(file)

                    if (mimeType == null) {
                        Log.e("file error", "Not able to get mime type")
                        return@Thread
                    }

                    requestBodyBuilder
                        .addFormDataPart("uploaded_file", file.name, file.asRequestBody(getMimeType(file)?.toMediaTypeOrNull()))
                }

                val requestBody = requestBodyBuilder.build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload","success")
                    showToast("File uploaded successfully")
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
            // toggleProgressDialog(false)
        }.start()
    }

     */

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    /*
    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText( activity, message, Toast.LENGTH_LONG ).show()
        }
    }

     */

}