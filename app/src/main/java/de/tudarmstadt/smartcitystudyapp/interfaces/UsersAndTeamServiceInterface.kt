package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.TeamModel
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.models.UserAndTeamModel
import kotlinx.coroutines.flow.Flow

interface UsersAndTeamServiceInterface {
    /*
    suspend fun getUsers() : List<UsersAndTeam>
     */

    fun getByTeamId(teamId: String): Flow<UserAndTeamModel>

    suspend fun addUser(user: UserModel)

    suspend fun addTeam(team: TeamModel)


}