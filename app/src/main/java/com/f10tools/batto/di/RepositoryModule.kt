package com.f10tools.batto.di

import com.f10tools.batto.repository.BatteryEventRepository
import com.f10tools.batto.repository.BatteryEventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideBatteryEventRepositoryImpl(repository: BatteryEventRepositoryImpl): BatteryEventRepository
}