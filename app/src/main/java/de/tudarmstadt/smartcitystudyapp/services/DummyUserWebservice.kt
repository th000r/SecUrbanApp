package de.tudarmstadt.smartcitystudyapp.services

import kotlinx.coroutines.delay

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

val dummyRemoteUserDB = mapOf(
    "exmplUsr1" to StudyGroup.GROUP_1,
    "exmplUsr2" to StudyGroup.GROUP_2
)

object DummyUserWebservice: UserWebservice {
    override suspend fun userIsValid(userId: String): Boolean {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return dummyRemoteUserDB.containsKey(userId)
    }

    override suspend fun getStudyGroup(userId: String): StudyGroup? {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return dummyRemoteUserDB[userId]
    }

}