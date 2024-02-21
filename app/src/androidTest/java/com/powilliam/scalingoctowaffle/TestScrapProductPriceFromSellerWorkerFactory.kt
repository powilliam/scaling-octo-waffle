package com.powilliam.scalingoctowaffle

import android.content.Context
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.scalingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepositoryImpl
import com.powilliam.scalingoctowaffle.data.sources.local.PricingsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSourceImpl
import com.powilliam.scalingoctowaffle.data.workers.ScrapProductPriceFromSellerWorker
import org.jsoup.helper.HttpConnection

class TestScrapProductPriceFromSellerWorkerFactory(
    private val pricings: PricingDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ) = ScrapProductPriceFromSellerWorker(
        appContext,
        workerParameters,
        HyperTextsRepositoryImpl(
            HyperTextsRemoteDataSourceImpl(HttpConnection())
        ),
        SellersRepositoryImpl(
            SellersRemoteDataSourceImpl(Firebase.database)
        ),
        PricingsRepositoryImpl(
            PricingsLocalDataSourceImpl(pricings)
        )
    )
}