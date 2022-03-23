package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.UsersAndTeamDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UsersAndTeamService
import de.tudarmstadt.smartcitystudyapp.models.Team
import de.tudarmstadt.smartcitystudyapp.models.User
import de.tudarmstadt.smartcitystudyapp.models.UsersAndTeam
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

    override fun getByTeamId(teamId: String): Flow<UsersAndTeam> = usersAndTeamDao.getByTeamId(teamId)

    override suspend fun addUser(user: User) = withContext(Dispatchers.IO){
        usersAndTeamDao.save(user)
    }

    override suspend fun addTeam(team: Team) {
        usersAndTeamDao.save(Team(team.teamId, team.teamName, team.points))
    }
}