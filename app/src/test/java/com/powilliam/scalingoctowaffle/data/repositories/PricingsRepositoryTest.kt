package com.powilliam.scalingoctowaffle.data.repositories

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class PricingsRepositoryTest {
    @Test
    fun itShouldBeAbleToGetAllPricingsByProductId() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricing = Pricing.forTesting(productId = product.id)

        val dataSource = mock<PricingsLocalDataSource> {
            on { allByProductId(product.id) } doReturn flowOf(listOf(pricing))
        }
        val repository = PricingsRepositoryImpl(dataSource)

        repository.allByProductId(product.id).test {
            val items = awaitItem()
            assert(items.contains(pricing))
            awaitComplete()
        }
    }

    @Test
    fun itShouldBeAbleToInsertAPricing() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricing = Pricing.forTesting(productId = product.id)

        val pricings = mutableListOf<Pricing>()

        val dataSource = mock<PricingsLocalDataSource> {
            onBlocking { insert(pricing) } doAnswer {
                pricings.add(pricing)
                return@doAnswer
            }
        }
        val repository = PricingsRepositoryImpl(dataSource)

        repository.insert(pricing)

        assert(pricings.contains(pricing))
    }
}