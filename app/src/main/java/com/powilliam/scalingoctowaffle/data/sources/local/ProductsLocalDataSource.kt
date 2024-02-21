package com.powilliam.scalingoctowaffle.data.sources.local

import com.powilliam.scalingoctowaffle.data.database.daos.ProductDao
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ProductsLocalDataSource {
    fun allWithPricings(): Flow<List<ProductWithPricings>>

    suspend fun byHref(href: String): Product

    suspend fun insert(product: Product)

    suspend fun delete(product: Product)
}

class ProductsLocalDataSourceImpl @Inject constructor(
    private val dao: ProductDao
) : ProductsLocalDataSource {
    override fun allWithPricings(): Flow<List<ProductWithPricings>> = dao.allWithPricings()

    override suspend fun byHref(href: String): Product = withContext(Dispatchers.IO) {
        dao.byHref(href)
    }

    override suspend fun insert(product: Product) = withContext(Dispatchers.IO) {
        dao.insert(product)
    }

    override suspend fun delete(product: Product) = withContext(Dispatchers.IO) {
        dao.delete(product)
    }
}