package com.powilliam.scalingoctowaffle.di

import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepository
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepository
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepository
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepository
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepositoryImpl
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideProductsRepository(
        hyperTexts: HyperTextsRemoteDataSource,
        products: ProductsLocalDataSource
    ): ProductsRepository = ProductsRepositoryImpl(hyperTexts, products)

    @Singleton
    @Provides
    fun providePricingsRepository(dataSource: PricingsLocalDataSource): PricingsRepository =
        PricingsRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideSellersRepository(dataSource: SellersRemoteDataSource): SellersRepository =
        SellersRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideHyperTextsRepository(dataSource: HyperTextsRemoteDataSource): HyperTextsRepository =
        HyperTextsRepositoryImpl(dataSource)
}