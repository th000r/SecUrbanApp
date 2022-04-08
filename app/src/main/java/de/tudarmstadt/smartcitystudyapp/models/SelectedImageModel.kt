package de.tudarmstadt.smartcitystudyapp.models

import android.net.Uri

data class SelectedImageModel constructor(
    val id: Int,
    val path: String?,
    val uri: Uri?
)
