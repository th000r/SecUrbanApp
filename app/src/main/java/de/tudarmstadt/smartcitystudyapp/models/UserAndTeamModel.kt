package de.tudarmstadt.smartcitystudyapp.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndTeamModel(
    @Embedded
    val team: TeamModel,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamId"
    )
    val users: List<UserModel>? = null
)