package com.powilliam.scalingoctowaffle

import android.content.Context
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.scalingoctowaffle.data.database.daos.ProductDao
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepositoryImpl
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepositoryImpl
import com.powilliam.scalingoctowaffle.data.sources.local.ProductsLocalDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSourceImpl
import com.powilliam.scalingoctowaffle.data.sources.remote.SellersRemoteDataSourceImpl
import com.powilliam.scalingoctowaffle.data.workers.InsertProductWorker
import org.jsoup.helper.HttpConnection

class TestInsertProductWorkerFactory(
    private val products: ProductDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ) = InsertProductWorker(
        appContext,
        workerParameters,
        HyperTextsRepositoryImpl(
            HyperTextsRemoteDataSourceImpl(HttpConnection())
        ),
        SellersRepositoryImpl(
            SellersRemoteDataSourceImpl(Firebase.database)
        ),
        ProductsRepositoryImpl(
            ProductsLocalDataSourceImpl(products)
        )
    )
}