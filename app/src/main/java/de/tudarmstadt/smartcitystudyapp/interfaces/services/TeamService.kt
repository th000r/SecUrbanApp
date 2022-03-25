package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.TeamModel

interface TeamService {
    suspend fun getTeamId() : String?

    suspend fun setTeam(team: TeamModel)

    suspend fun addPoints(team: TeamModel, points: Int)
}