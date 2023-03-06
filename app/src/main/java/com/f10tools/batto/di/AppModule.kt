package com.f10tools.batto.di

import android.content.Context
import androidx.work.WorkManager
import com.f10tools.batto.database.createDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context) = createDb(appContext)

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager = WorkManager.getInstance(appContext)
}