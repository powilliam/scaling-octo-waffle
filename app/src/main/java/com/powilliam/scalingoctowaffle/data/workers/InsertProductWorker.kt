package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepository
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepository
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class InsertProductWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val hyperTexts: HyperTextsRepository,
    private val sellers: SellersRepository,
    private val products: ProductsRepository,
) : CoroutineWorker(context, params) {
    override suspend fun doWork() = withContext(Dispatchers.IO) {
        try {
            val href = params.inputData.getString("href") ?: return@withContext Result.failure(
                workDataOf("reason" to MISSING_HREF)
            )
            val uri = Uri.parse(href)
            val host = uri.host ?: return@withContext Result.failure(
                workDataOf("reason" to UNDEFINED_HOST)
            )

            sellers.byHost(host) ?: return@withContext Result.failure(
                workDataOf("reason" to UNSUPPORTED_SELLER)
            )

            val document = hyperTexts.byHref(href)

            val product = Product(name = document.title(), href = href)

            products.insert(product)

            val inserted = products.byHref(product.href)

            Result.success(
                workDataOf(
                    "productId" to inserted.id,
                    "href" to inserted.href
                )
            )
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val MISSING_HREF = "MISSING_HREF"
        const val UNDEFINED_HOST = "UNDEFINED_HOST"
        const val UNSUPPORTED_SELLER = "UNSUPPORTED_SELLER"
    }
}