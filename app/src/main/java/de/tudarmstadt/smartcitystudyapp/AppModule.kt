package de.tudarmstadt.smartcitystudyapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import de.tudarmstadt.smartcitystudyapp.services.DummyReportService
import de.tudarmstadt.smartcitystudyapp.services.ReportService
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideReportService(): ReportService {
        return DummyReportService
    }
}