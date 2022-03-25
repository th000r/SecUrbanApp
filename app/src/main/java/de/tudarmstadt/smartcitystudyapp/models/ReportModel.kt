package de.tudarmstadt.smartcitystudyapp.models

var SOURCE_OTHER = "Eigeninitiative"

data class ReportModel constructor(
    val userId: String,
    val message: String,
    val location: Boolean = false,
    val latitude: Double = -1.0,
    val longitude: Double = -1.0,
    val picture: Boolean = false,
    val source: String = SOURCE_OTHER
)
