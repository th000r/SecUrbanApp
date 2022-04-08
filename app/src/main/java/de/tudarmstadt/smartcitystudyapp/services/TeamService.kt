package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.database.TeamDao
import de.tudarmstadt.smartcitystudyapp.interfaces.TeamServiceInterface
import de.tudarmstadt.smartcitystudyapp.models.TeamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamService @Inject constructor(
    private val teamDao: TeamDao
) : TeamServiceInterface {
    override suspend fun getTeamId(): String? = withContext(Dispatchers.IO){
        teamDao.loadAll().let {
            if (it.isEmpty()) {
                null
            } else {
                it.first().teamId
            }
        }
    }

    override suspend fun setTeam(team: TeamModel) = withContext(Dispatchers.IO){
        teamDao.save(team)
    }

    override suspend fun addPoints(team: TeamModel, points: Int) {
        teamDao.save(TeamModel(team.teamId, team.teamName, team.points+points))
    }
}