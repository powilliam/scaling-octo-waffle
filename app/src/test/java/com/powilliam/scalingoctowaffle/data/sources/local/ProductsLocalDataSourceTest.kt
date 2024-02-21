package com.powilliam.scalingoctowaffle.data.sources.local

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.database.daos.ProductDao
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ProductsLocalDataSourceTest {
    @Test
    fun itShouldBeAbleToListAllProductsWithPricings() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricings = listOf(Pricing.forTesting(productId = product.id))
        val productsWithPricings = listOf(ProductWithPricings(product, pricings))

        val dao = mock<ProductDao> {
            on { allWithPricings() } doReturn flowOf(productsWithPricings)
        }

        val dataSource = ProductsLocalDataSourceImpl(dao)

        dataSource.allWithPricings().test {
            val item = awaitItem()
            assert(item == productsWithPricings)
            awaitComplete()
        }
    }

    @Test
    fun itShouldBeAbleToGetProductByHref() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val dao = mock<ProductDao> {
            onBlocking { byHref(product.href) } doReturn product
        }

        val dataSource = ProductsLocalDataSourceImpl(dao)

        val result = dataSource.byHref(product.href)

        assert(product == result)
    }

    @Test
    fun itShouldBeAbleToInsertAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val products = mutableListOf<Product>()

        val dao = mock<ProductDao> {
            onBlocking { insert(product) } doAnswer {
                products.add(product)
                return@doAnswer
            }
        }

        val dataSource = ProductsLocalDataSourceImpl(dao)

        dataSource.insert(product)

        assert(products.contains(product))
    }

    @Test
    fun itShouldBeAbleToDeleteAProduct() = runBlocking {
        val product = Product.forTesting(name = "Something")

        val products = mutableListOf(product)

        val dao = mock<ProductDao> {
            onBlocking { delete(product) } doAnswer {
                products.remove(product)
                return@doAnswer
            }
        }

        val dataSource = ProductsLocalDataSourceImpl(dao)

        dataSource.delete(product)

        assert(!products.contains(product))
    }
}