package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.UserWebserviceInterface

object DummyUserWebservice : UserWebserviceInterface {
    override suspend fun registerUser(userId: String) {
        //Do nothing.
        //Actual implementation sends a message to backend server notifying about the new user joining the study.
    }
}