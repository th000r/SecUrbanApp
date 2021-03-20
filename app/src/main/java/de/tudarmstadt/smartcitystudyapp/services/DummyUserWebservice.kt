package de.tudarmstadt.smartcitystudyapp.services

import kotlinx.coroutines.delay

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

object DummyUserWebservice: UserWebservice {
    override suspend fun userIsValid(userId: String): Boolean {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return true
    }
}