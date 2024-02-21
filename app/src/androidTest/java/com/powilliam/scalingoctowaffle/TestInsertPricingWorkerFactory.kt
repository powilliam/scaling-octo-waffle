package com.powilliam.scalingoctowaffle

import android.content.Context
import androidx.room.Room
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.powilliam.scalingoctowaffle.data.database.AppDatabase
import com.powilliam.scalingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.workers.InsertPricingWorker

class TestInsertPricingWorkerFactory(
    private val pricing: PricingDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ) = InsertPricingWorker(
        appContext,
        workerParameters,
        PricingsRepositoryImpl(
            PricingsLocalDataSourceImpl(pricing)
        )
    )
}