package com.powilliam.scalingoctowaffle.data.sources.local

import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class PricingsLocalDataSourceTest {
    @Test
    fun itShouldBeAbleToGetAllPricingsByProductId() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricings = listOf(Pricing.forTesting(productId = product.id))

        val dao = mock<PricingDao> {
            on { allByProductId(product.id) } doReturn flowOf(pricings)
        }
        val dataSource = PricingsLocalDataSourceImpl(dao)

        dataSource.allByProductId(product.id).test {
            val item = awaitItem()
            assert(item == pricings)
            awaitComplete()
        }
    }

    @Test
    fun itShouldBeAbleToInsertAPricing() = runBlocking {
        val product = Product.forTesting(name = "Something")
        val pricing = Pricing.forTesting(productId = product.id)

        val pricings = mutableListOf<Pricing>()

        val dao = mock<PricingDao> {
            onBlocking { insert(pricing) } doAnswer {
                pricings.add(pricing)
                return@doAnswer
            }
        }

        val dataSource = PricingsLocalDataSourceImpl(dao)

        dataSource.insert(pricing)

        assert(pricings.contains(pricing))
    }
}