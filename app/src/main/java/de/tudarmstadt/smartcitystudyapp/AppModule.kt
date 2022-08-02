package de.tudarmstadt.smartcitystudyapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.tudarmstadt.smartcitystudyapp.database.AppDatabase
import de.tudarmstadt.smartcitystudyapp.interfaces.*
import de.tudarmstadt.smartcitystudyapp.services.*
import de.tudarmstadt.smartcitystudyapp.services.DummyUserWebservice
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideReportService(): ReportServiceInterface {
        return ReportService
    }

    @Singleton
    @Provides
    fun provideUserWebservice(): UserWebserviceInterface {
        return DummyUserWebservice
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "SmartCityStudy.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserService(
        webserviceInterface: UserWebserviceInterface,
        database: AppDatabase
    ): UserServiceInterface {
        return UserService(webserviceInterface, database.userDao())
    }

    @Singleton
    @Provides
    fun provideActivitiesService(
        database: AppDatabase
    ): ActivitiesServiceInterface {
        return ActivitiesService(database.activitiesDao())
    }

    @Singleton
    @Provides
    fun provideNotificationService(
        database: AppDatabase
    ): NotificationServiceInterface {
        return NotificationService(database.notificationDao())
    }

    @Singleton
    @Provides
    fun provideTeamService(
        database: AppDatabase
    ): TeamServiceInterface {
        return TeamService(database.teamDao())
    }


    @Singleton
    @Provides
    fun provideUsersAndTeamService(
        database: AppDatabase
    ): UsersAndTeamServiceInterface {
        return UsersAndTeamService(database.usersAndTeamDao())
    }

    @Singleton
    @Provides
    fun providePreparednessChecklistService(
        database: AppDatabase
    ): PreparednessChecklistServiceInterface {
        return PreparednessChecklistService(database.preparednessChecklistDao())
    }
}