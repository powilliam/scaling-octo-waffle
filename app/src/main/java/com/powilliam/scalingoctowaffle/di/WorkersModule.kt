package com.powilliam.scalingoctowaffle.di

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ScallingOctoWaffleWorkerConfiguration

@Module
@InstallIn(SingletonComponent::class)
object WorkersModule {

    @ScallingOctoWaffleWorkerConfiguration
    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(factory: HiltWorkerFactory): Configuration =
        Configuration.Builder().setWorkerFactory(factory).build()
}