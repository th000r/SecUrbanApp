package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.interfaces.services.UserWebservice

object DummyUserWebservice : UserWebservice {
    override suspend fun registerUser(userId: String) {
        //Do nothing.
        //Actual implementation sends a message to backend server notifying about the new user joining the study.
    }
}