package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.TeamDao
import de.tudarmstadt.smartcitystudyapp.interfaces.services.TeamService
import de.tudarmstadt.smartcitystudyapp.models.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTeamService @Inject constructor(
    private val teamDao: TeamDao
) : TeamService {
    override suspend fun getTeamId(): String? = withContext(Dispatchers.IO){
        teamDao.loadAll().let {
            if (it.isEmpty()) {
                null
            } else {
                it.first().teamId
            }
        }
    }

    override suspend fun setTeam(team: Team) = withContext(Dispatchers.IO){
        teamDao.save(team)
    }

    override suspend fun addPoints(team: Team, points: Int) {
        teamDao.save(Team(team.teamId, team.teamName, team.points+points))
    }
}