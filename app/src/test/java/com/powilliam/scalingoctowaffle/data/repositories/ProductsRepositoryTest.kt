package com.powilliam.scalingoctowaffle.data.repositories

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSource
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSourceImpl
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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

        val dataSource = mock<ProductsLocalDataSourceImpl> {
            on { allWithPricings() } doReturn flowOf(listOf(productWithPricings))
        }
        val repository = ProductsRepositoryImpl(dataSource)

        repository.allWithPricings().test {
            val items = awaitItem()
            assert(items.contains(productWithPricings))
            awaitComplete()
        }
    }

    @Test
    fun itShouldBeAbleToGetProductByHref() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val dataSource = mock<ProductsLocalDataSourceImpl> {
            onBlocking { byHref(product.href) } doReturn product
        }

        val repository = ProductsRepositoryImpl(dataSource)

        val result = repository.byHref(product.href)

        assert(result == product)
    }


    @Test
    fun itShouldBeAbleToInsertAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val products = mutableListOf<Product>()

        val dataSource = mock<ProductsLocalDataSource> {
            onBlocking { insert(product) } doAnswer {
                products.add(product)
                return@doAnswer
            }
        }
        val repository = ProductsRepositoryImpl(dataSource)

        repository.insert(product)

        assert(products.contains(product))
    }

    @Test
    fun itShouldBeAbleToDeleteAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val products = mutableListOf(product)

        val dataSource = mock<ProductsLocalDataSource> {
            onBlocking { delete(product) } doAnswer {
                products.remove(product)
                return@doAnswer
            }
        }
        val repository = ProductsRepositoryImpl(dataSource)

        repository.delete(product)

        assert(!products.contains(product))
    }
}