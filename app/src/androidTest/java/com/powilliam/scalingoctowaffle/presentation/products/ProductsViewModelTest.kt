package com.powilliam.scalingoctowaffle.presentation.products

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ProductsViewModelTest {
    @Test
    fun itShouldBeAbleToGetAllProductsWithPricings() = runBlocking {
        val product = Product.forTesting(name = "My Product")
        val pricing = Pricing.forTesting(productId = product.id)
        val productWithPricings = ProductWithPricings(product, listOf(pricing))

        val repository = mock<ProductsRepository> {
            on { allWithPricings() } doReturn flowOf(listOf(productWithPricings))
        }

        val productsViewModel = ProductsViewModel(repository)

        productsViewModel.allWithPricings.test {
            val items = awaitItem()
            assert(items.contains(productWithPricings))
        }
    }

    @Test
    fun itShouldBeAbleToInsertProductAndSchedulePeriodicWorker() {
        val product = Product.forTesting(
            name = "Echo Dot 5",
            href = "https://www.amazon.com.br/Echo-Dot-5ª-geração-Cor-Preta/dp/B09B8VGCR8"
        )

        val repository = mock<ProductsRepository> {
            onBlocking { byHref(product.href) } doReturn product
            onBlocking { insert(product.href) } doAnswer {}
        }

        val productsViewModel = ProductsViewModel(repository)

        productsViewModel.onInsert(product.href) {
            assert(it == product)
        }
    }
}