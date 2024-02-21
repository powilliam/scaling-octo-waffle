package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductsRepository {
    fun allWithPricings(): Flow<List<ProductWithPricings>>
    suspend fun byHref(href: String): Product
    suspend fun insert(href: String)
    suspend fun delete(product: Product)
}

class ProductsRepositoryImpl @Inject constructor(
    private val hyperTexts: HyperTextsRemoteDataSource,
    private val products: ProductsLocalDataSource,
) : ProductsRepository {
    override fun allWithPricings() = products.allWithPricings()

    override suspend fun byHref(href: String) = products.byHref(href)

    override suspend fun insert(href: String) {
        val document = hyperTexts.byHref(href)
        val product = Product(name = document.title(), href = href)

        products.insert(product)
    }

    override suspend fun delete(product: Product) = products.delete(product)

}