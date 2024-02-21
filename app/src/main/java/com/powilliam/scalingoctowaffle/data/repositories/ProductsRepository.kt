package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductsRepository {
    fun allWithPricings(): Flow<List<ProductWithPricings>>
    suspend fun insert(product: Product)
    suspend fun delete(product: Product)
}

class ProductsRepositoryImpl @Inject constructor(
    private val dataSource: ProductsLocalDataSource
) : ProductsRepository {
    override fun allWithPricings() = dataSource.allWithPricings()

    override suspend fun insert(product: Product) = dataSource.insert(product)

    override suspend fun delete(product: Product) = dataSource.delete(product)
}