package de.tudarmstadt.smartcitystudyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.tudarmstadt.smartcitystudyapp.models.*

@Database(entities = [UserModel::class, ActivityModel::class, NotificationModel::class, TeamModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context?): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context!!,AppDatabase::class.java, "SmartCityStudy.db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun userDao(): UserDao

    abstract fun activitiesDao(): ActivitiesDao

    abstract fun notificationDao(): NotificationDao

    abstract fun teamDao(): TeamDao

    abstract fun usersAndTeamDao(): UsersAndTeamDao
}
