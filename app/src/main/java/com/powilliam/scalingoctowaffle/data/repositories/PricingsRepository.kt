package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PricingsRepository {
    fun allByProductId(productId: Int): Flow<List<Pricing>>

    suspend fun insert(pricing: Pricing)
}

class PricingsRepositoryImpl @Inject constructor(
    private val dataSource: PricingsLocalDataSource
) : PricingsRepository {
    override fun allByProductId(productId: Int) = dataSource.allByProductId(productId)

    override suspend fun insert(pricing: Pricing) = dataSource.insert(pricing)
}