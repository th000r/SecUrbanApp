package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.tudarmstadt.smartcitystudyapp.model.ActivityEntry
import de.tudarmstadt.smartcitystudyapp.model.User

@Database(entities = [User::class, ActivityEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun activitiesDao(): ActivitiesDao
}
