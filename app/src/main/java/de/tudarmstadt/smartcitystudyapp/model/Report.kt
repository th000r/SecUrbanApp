package de.tudarmstadt.smartcitystudyapp.model

const val SOURCE_OTHER = "Eigeninitiative"

data class Report(
    val userId: String,
    val message: String,
    val location: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val picture: Boolean = false,
    val source: String = SOURCE_OTHER
)
