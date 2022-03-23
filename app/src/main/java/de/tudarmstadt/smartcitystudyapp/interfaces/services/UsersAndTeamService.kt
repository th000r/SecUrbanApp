package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.Team
import de.tudarmstadt.smartcitystudyapp.models.User
import de.tudarmstadt.smartcitystudyapp.models.UsersAndTeam
import kotlinx.coroutines.flow.Flow

interface UsersAndTeamService {
    /*
    suspend fun getUsers() : List<UsersAndTeam>
     */

    fun getByTeamId(teamId: String): Flow<UsersAndTeam>

    suspend fun addUser(user: User)

    suspend fun addTeam(team: Team)


}