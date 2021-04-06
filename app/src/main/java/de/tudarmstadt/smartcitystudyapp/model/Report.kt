package de.tudarmstadt.smartcitystudyapp.model

data class Report(
    val userId: String,
    val message: String,
    val latitude: Double,
    val longitude: Double,
    val picture: Boolean
)
