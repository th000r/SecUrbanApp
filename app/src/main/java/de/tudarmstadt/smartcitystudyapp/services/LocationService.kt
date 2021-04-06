package de.tudarmstadt.smartcitystudyapp.services

interface LocationService {
    fun getLocation(): Pair<Double, Double>

    fun locationEnabled(): Boolean
}