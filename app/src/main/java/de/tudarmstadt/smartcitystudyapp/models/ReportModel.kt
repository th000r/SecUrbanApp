package de.tudarmstadt.smartcitystudyapp.models

import com.google.gson.annotations.SerializedName

var SOURCE_OTHER = "Eigeninitiative"

data class ReportModel constructor(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("location")
    val location: Boolean = false,
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("picture")
    val picture: Boolean = false,
    @SerializedName("source")
    val source: String = SOURCE_OTHER
)
