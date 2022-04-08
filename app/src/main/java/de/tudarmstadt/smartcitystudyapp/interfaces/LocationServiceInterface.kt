package de.tudarmstadt.smartcitystudyapp.interfaces

interface LocationServiceInterface {
    fun getLocation(): Pair<Double, Double>

    fun locationEnabled(): Boolean
}