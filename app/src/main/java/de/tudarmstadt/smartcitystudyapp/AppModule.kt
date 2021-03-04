package de.tudarmstadt.smartcitystudyapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.tudarmstadt.smartcitystudyapp.database.UserDatabase
import de.tudarmstadt.smartcitystudyapp.services.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideReportService(): ReportService {
        return DummyReportService
    }

    @Singleton
    @Provides
    fun provideUserWebservice(): UserWebservice {
        return DummyUserWebservice
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserService(
        webservice: UserWebservice,
        database: UserDatabase
    ): UserService {
        return DefaultUserService(webservice, database.userDao())
    }
}