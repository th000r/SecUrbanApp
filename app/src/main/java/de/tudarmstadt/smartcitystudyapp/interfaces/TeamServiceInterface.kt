package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.models.TeamModel

interface TeamServiceInterface {
    suspend fun getTeamId() : String?

    suspend fun setTeam(team: TeamModel)

    suspend fun addPoints(team: TeamModel, points: Int)
}