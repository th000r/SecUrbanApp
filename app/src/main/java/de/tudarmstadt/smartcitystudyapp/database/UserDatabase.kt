package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.tudarmstadt.smartcitystudyapp.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
