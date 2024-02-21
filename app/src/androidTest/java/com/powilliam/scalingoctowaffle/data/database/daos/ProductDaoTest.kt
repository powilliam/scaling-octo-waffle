package com.powilliam.scalingoctowaffle.data.database.daos

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.CoroutineTestDispatcherRule
import com.powilliam.scalingoctowaffle.data.database.AppDatabase
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var database: AppDatabase

    @get:Rule
    val rule = CoroutineTestDispatcherRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun teardown() = database.close()

    @Test
    fun itShouldBeAbleToInsertAndListAllProductsWithPricings() = runTest {
        val (job, product, pricing) = seed(database)

        job.await()

        database.product().allWithPricings().test {
            val item = awaitItem()
            assert(item.any { it.product == product && it.pricings.contains(pricing) })
        }
    }

    @Test
    fun itShouldBeAbleToDeleteAProduct() = runTest {
        val (seedJob, product) = seed(database)
        val deleteJob = async { database.product().delete(product) }

        seedJob.await()
        deleteJob.await()

        database.product().allWithPricings().test {
            val item = awaitItem()
            assert(item.isEmpty())
        }
    }

    companion object {
        fun CoroutineScope.seed(database: AppDatabase): Triple<Deferred<*>, Product, Pricing> {
            val product = Product.forTesting(name = "Something")
            val pricing = Pricing.forTesting(productId = product.id)

            val job = async {
                database.withTransaction {
                    database.product().insert(product)
                    database.pricing().insert(pricing)
                }
            }

            return Triple(job, product, pricing)
        }
    }
}