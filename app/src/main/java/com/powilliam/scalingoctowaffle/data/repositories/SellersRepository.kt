package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.entities.Seller
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSource
import javax.inject.Inject

interface SellersRepository {
    suspend fun byHost(host: String): Seller?
}

class SellersRepositoryImpl @Inject constructor(
    private val dataSource: SellersRemoteDataSource
) : SellersRepository {
    override suspend fun byHost(host: String) = dataSource.byHost(host)
}