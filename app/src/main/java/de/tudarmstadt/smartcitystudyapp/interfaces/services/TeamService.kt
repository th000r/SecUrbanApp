package de.tudarmstadt.smartcitystudyapp.interfaces.services

import de.tudarmstadt.smartcitystudyapp.models.Team

interface TeamService {
    suspend fun getTeamId() : String?

    suspend fun setTeam(team: Team)

    suspend fun addPoints(team: Team, points: Int)
}