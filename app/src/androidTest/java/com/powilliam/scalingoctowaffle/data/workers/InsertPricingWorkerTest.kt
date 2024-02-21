package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import app.cash.turbine.test
import com.powilliam.scalingoctowaffle.TestInsertPricingWorkerFactory
import com.powilliam.scalingoctowaffle.data.database.AppDatabase
import com.powilliam.scalingoctowaffle.data.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class InsertPricingWorkerTest {
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
    }

    @After
    fun teardown() = database.close()

    @Test
    fun itShouldBeAbleToInsertAPricing() = runTest {
        val product = seed(database)
        val data = workDataOf("productId" to product.id, "price" to 3500)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val worker = TestListenableWorkerBuilder<InsertPricingWorker>(context, inputData = data)
            .setWorkerFactory(TestInsertPricingWorkerFactory(database.pricing()))
            .build()

        val result = worker.doWork()

        assert(result is ListenableWorker.Result.Success)

        database.pricing().allByProductId(product.id).test {
            val items = awaitItem()
            assert(items.isNotEmpty())
        }
    }

    companion object {
        fun CoroutineScope.seed(database: AppDatabase): Product {
            val forTesting = Product.forTesting(name = "Fake Product")
            launch { database.product().insert(forTesting) }
            return forTesting
        }
    }
}