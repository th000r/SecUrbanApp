package de.tudarmstadt.smartcitystudyapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class UsersAndTeam(
    @Embedded
    val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamId"
    )
    val users: List<User>? = null
)