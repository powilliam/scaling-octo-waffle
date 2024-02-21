package com.powilliam.scalingoctowaffle.di

import com.google.firebase.database.FirebaseDatabase
import com.powilliam.scalingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scalingoctowaffle.data.database.daos.ProductDao
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSource
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jsoup.Connection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Singleton
    @Provides
    fun provideProductsLocalDataSource(product: ProductDao): ProductsLocalDataSource =
        ProductsLocalDataSourceImpl(product)

    @Singleton
    @Provides
    fun providePricingsLocalDataSource(pricing: PricingDao): PricingsLocalDataSource =
        PricingsLocalDataSourceImpl(pricing)

    @Singleton
    @Provides
    fun provideSellersRemoteDataSource(database: FirebaseDatabase): SellersRemoteDataSource =
        SellersRemoteDataSourceImpl(database)

    @Singleton
    @Provides
    fun provideHyperTextsRemoteDataSource(@HyperTextsClient client: Connection): HyperTextsRemoteDataSource =
        HyperTextsRemoteDataSourceImpl(client)
}