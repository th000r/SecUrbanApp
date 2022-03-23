package de.tudarmstadt.smartcitystudyapp.services

import de.tudarmstadt.smartcitystudyapp.model.Team

interface TeamService {
    suspend fun getTeamId() : String?

    suspend fun setTeam(team: Team)

    suspend fun addPoints(team: Team, points: Int)
}