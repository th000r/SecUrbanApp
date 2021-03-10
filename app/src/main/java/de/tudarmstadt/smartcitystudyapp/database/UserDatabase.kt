package de.tudarmstadt.smartcitystudyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.tudarmstadt.smartcitystudyapp.model.Converters
import de.tudarmstadt.smartcitystudyapp.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
