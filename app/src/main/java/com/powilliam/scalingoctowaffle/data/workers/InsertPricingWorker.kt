package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class InsertPricingWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repository: PricingsRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val productId = params.inputData.getInt("productId", -1)
            val price = params.inputData.getDouble("price", 0.0)

            val pricing = Pricing(productId = productId, inCents = price * 100)

            repository.insert(pricing)

            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}