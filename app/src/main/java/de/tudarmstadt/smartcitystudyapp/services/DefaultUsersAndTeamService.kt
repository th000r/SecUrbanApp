package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.UsersAndTeamDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UsersAndTeamService
import de.tudarmstadt.smartcitystudyapp.models.TeamModel
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.models.UserAndTeamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultUsersAndTeamService @Inject constructor(
    private val usersAndTeamDao: UsersAndTeamDao
) : UsersAndTeamService {
    /*
    override suspend fun getUsers(): List<UsersAndTeam> {
        TODO("Not yet implemented")
    }
     */

    override fun getByTeamId(teamId: String): Flow<UserAndTeamModel> = usersAndTeamDao.getByTeamId(teamId)

    override suspend fun addUser(user: UserModel) = withContext(Dispatchers.IO){
        usersAndTeamDao.save(user)
    }

    override suspend fun addTeam(team: TeamModel) {
        usersAndTeamDao.save(TeamModel(team.teamId, team.teamName, team.points))
    }
}