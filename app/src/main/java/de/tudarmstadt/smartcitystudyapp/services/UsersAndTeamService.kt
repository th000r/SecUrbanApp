package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Team
import de.tudarmstadt.smartcitystudyapp.model.User
import de.tudarmstadt.smartcitystudyapp.model.UsersAndTeam
import kotlinx.coroutines.flow.Flow

interface UsersAndTeamService {
    /*
    suspend fun getUsers() : List<UsersAndTeam>
     */

    fun getByTeamId(teamId: String): Flow<UsersAndTeam>

    suspend fun addUser(user: User)

    suspend fun addTeam(team: Team)


}