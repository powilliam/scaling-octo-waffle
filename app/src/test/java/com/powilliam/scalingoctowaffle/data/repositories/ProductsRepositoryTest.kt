package com.powilliam.scalingoctowaffle.data.repositories

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.jsoup.nodes.Document
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ProductsRepositoryTest {

    @Test
    fun itShouldBeAbleToGetAllProductsWithPricings() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricing = Pricing.forTesting(productId = product.id)
        val productWithPricings = ProductWithPricings(product, listOf(pricing))

        val hyperTexts = mock<HyperTextsRemoteDataSource> {}

        val dataSource = mock<ProductsLocalDataSourceImpl> {
            on { allWithPricings() } doReturn flowOf(listOf(productWithPricings))
        }

        val repository = ProductsRepositoryImpl(hyperTexts, dataSource)

        repository.allWithPricings().test {
            val items = awaitItem()
            assert(items.contains(productWithPricings))
            awaitComplete()
        }
    }

    @Test
    fun itShouldBeAbleToGetProductByHref() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val hyperTexts = mock<HyperTextsRemoteDataSource> {}

        val dataSource = mock<ProductsLocalDataSourceImpl> {
            onBlocking { byHref(product.href) } doReturn product
        }

        val repository = ProductsRepositoryImpl(hyperTexts, dataSource)

        val result = repository.byHref(product.href)

        assert(result == product)
    }


    @Test
    fun itShouldBeAbleToInsertAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val list = mutableListOf<Product>()

        val hyperTexts = mock<HyperTextsRemoteDataSource> {
            onBlocking { byHref(product.href) } doReturn object : Document(product.href) {
                override fun title() = "Something"
            }
        }

        val products = mock<ProductsLocalDataSource> {
            onBlocking { insert(product) } doAnswer {
                list.add(product)
                return@doAnswer
            }
        }

        val repository = ProductsRepositoryImpl(hyperTexts, products)

        repository.insert(product.href)

        assert(list.any { it.href == product.href })
    }

    @Test
    fun itShouldBeAbleToDeleteAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val products = mutableListOf(product)

        val hyperTexts = mock<HyperTextsRemoteDataSource> {}

        val dataSource = mock<ProductsLocalDataSource> {
            onBlocking { delete(product) } doAnswer {
                products.remove(product)
                return@doAnswer
            }
        }

        val repository = ProductsRepositoryImpl(hyperTexts, dataSource)

        repository.delete(product)

        assert(!products.contains(product))
    }
}