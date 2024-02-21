package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.entities.Seller
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class SellersRepositoryTest {
    @Test
    fun itShouldBeAbleToFindSellerByHost() = runBlocking {
        val seller = Seller.forTesting(host = "ecommerce.com")

        val dataSource = mock<SellersRemoteDataSource> {
            onBlocking { byHost(seller.host) } doReturn seller
        }
        val repository = SellersRepositoryImpl(dataSource)

        assert(repository.byHost(seller.host) == seller)
    }
}