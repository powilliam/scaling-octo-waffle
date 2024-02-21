package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.powilliam.scalingoctowaffle.TestInsertProductWorkerFactory
import com.powilliam.scalingoctowaffle.data.database.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class InsertProductWorkerTest {
    private lateinit var context: Context
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    fun teardown() = database.close()

    @Test
    fun itShouldBeAbleToInsertProduct() = runTest {
        val data = workDataOf("href" to "https://www.amazon.com.br/Echo-Dot-5ª-geração-Cor-Preta/dp/B09B8VGCR8")
        val worker = TestListenableWorkerBuilder<InsertProductWorker>(context, data)
            .setWorkerFactory(TestInsertProductWorkerFactory(database.product()))
            .build()

        val result = worker.doWork()

        assert(result is ListenableWorker.Result.Success)
        assert(result.outputData.getInt("productId", -1) != -1)
    }

    @Test
    fun itShouldNotBeAbleToInsertProductIfHrefIsMissing() = runTest {
        val worker = TestListenableWorkerBuilder<InsertProductWorker>(context)
            .setWorkerFactory(TestInsertProductWorkerFactory(database.product()))
            .build()

        val result = worker.doWork()

        assert(result is ListenableWorker.Result.Failure)
        assert(result.outputData.getString("reason") == InsertProductWorker.MISSING_HREF)
    }

    @Test
    fun itShouldNotBeAbleToInsertProductIfSellerIsUnSupported() = runTest {
        val data = workDataOf("href" to "https://www.terabyteshop.com.br/produto/23002/ssd-kingston-nv2-500gb-m2-nvme-2280-leitura-3500mbs-e-gravacao-2100mbs-snv2s500g")
        val worker = TestListenableWorkerBuilder<InsertProductWorker>(context, data)
            .setWorkerFactory(TestInsertProductWorkerFactory(database.product()))
            .build()

        val result = worker.doWork()

        assert(result is ListenableWorker.Result.Failure)
        assert(result.outputData.getString("reason") == InsertProductWorker.UNSUPPORTED_SELLER)
    }
}