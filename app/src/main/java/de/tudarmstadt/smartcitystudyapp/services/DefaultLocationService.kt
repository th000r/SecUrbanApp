package de.tudarmstadt.smartcitystudyapp.services

class DefaultLocationService: LocationService {
    override fun getLocation(): Pair<Double, Double> {
        //TODO
        return Pair(0.0, 0.0)
    }

    override fun locationEnabled(): Boolean {
        //TODO
        return false
    }
}