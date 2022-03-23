package de.tudarmstadt.smartcitystudyapp.interfaces.services

interface LocationService {
    fun getLocation(): Pair<Double, Double>

    fun locationEnabled(): Boolean
}