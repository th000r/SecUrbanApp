package de.tudarmstadt.smartcitystudyapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.tudarmstadt.smartcitystudyapp.database.AppDatabase
import de.tudarmstadt.smartcitystudyapp.services.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideReportService(): ReportService {
        return DefaultReportService
    }

    @Singleton
    @Provides
    fun provideUserWebservice(): UserWebservice {
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
        webservice: UserWebservice,
        database: AppDatabase
    ): UserService {
        return DefaultUserService(webservice, database.userDao())
    }

    @Singleton
    @Provides
    fun provideActivitiesService(
        database: AppDatabase
    ): ActivitiesService {
        return DefaultActivitiesService(database.activitiesDao())
    }

    @Singleton
    @Provides
    fun provideNotificationService(
        database: AppDatabase
    ): NotificationService {
        return DefaultNotificationService(database.notificationDao())
    }
}