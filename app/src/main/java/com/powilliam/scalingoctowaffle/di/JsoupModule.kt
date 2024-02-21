package com.powilliam.scalingoctowaffle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jsoup.Connection
import org.jsoup.helper.HttpConnection
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HyperTextsClient

@Module
@InstallIn(SingletonComponent::class)
object JsoupModule {
    @HyperTextsClient
    @Singleton
    @Provides
    fun provideConnection(): Connection = HttpConnection()
}