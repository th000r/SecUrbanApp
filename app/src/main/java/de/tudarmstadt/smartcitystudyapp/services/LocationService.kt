package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.LocationServiceInterface

class LocationService: LocationServiceInterface {
    override fun getLocation(): Pair<Double, Double> {
        //TODO
        return Pair(0.0, 0.0)
    }

    override fun locationEnabled(): Boolean {
        //TODO
        return false
    }
}