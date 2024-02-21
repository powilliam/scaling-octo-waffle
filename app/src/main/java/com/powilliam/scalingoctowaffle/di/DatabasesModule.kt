package com.powilliam.scalingoctowaffle.di

import android.content.Context
import androidx.room.Room
import com.powilliam.scalingoctowaffle.data.database.AppDatabase
import com.powilliam.scalingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scalingoctowaffle.data.database.daos.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabasesModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.product()

    @Singleton
    @Provides
    fun providePricingDao(database: AppDatabase): PricingDao = database.pricing()
}