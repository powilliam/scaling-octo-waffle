package com.powilliam.scallingoctowaffle.data.sources.local

import com.powilliam.scallingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scallingoctowaffle.data.entities.Pricing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PricingsLocalDataSource {
    fun allByProductId(productId: Int): Flow<List<Pricing>>
    suspend fun insert(pricing: Pricing)
}

class PricingsLocalDataSourceImpl @Inject constructor(
    private val dao: PricingDao
) : PricingsLocalDataSource {
    override fun allByProductId(productId: Int): Flow<List<Pricing>> = dao.allByProductId(productId)

    override suspend fun insert(pricing: Pricing) = withContext(Dispatchers.IO) {
        dao.insert(pricing)
    }
}