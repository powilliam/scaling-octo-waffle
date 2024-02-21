package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepository
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

@HiltWorker
class ScrapProductPriceFromSellerWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val hyperTexts: HyperTextsRepository,
    private val sellers: SellersRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val productId = params.inputData.getInt("productId", -1)
            val href = params.inputData.getString("href") ?: return@withContext Result.failure()

            val sellerUri = Uri.parse(href)
            val host = sellerUri.host ?: return@withContext Result.failure()
            val seller = sellers.byHost(host) ?: return@withContext Result.failure()

            val document = hyperTexts.byHref(href)

            val element = document.getElementsByClass(seller.inDocumentPriceReference).first()

            val text = element?.text()?.fold("") { all, char ->
                all + seller.replaceAll.entries.fold(char.lowercase()) { everything, entry ->
                    everything.replace(entry.key, entry.value)
                }
            } ?: return@withContext Result.failure()

            Result.success(
                workDataOf(
                    "productId" to productId,
                    "price" to text.substring(seller.substringStartsAt.toInt()).toDouble(),
                )
            )
        } catch (exception: Exception) {
            Log.d("ScrapProductPriceFromSellerWorker", "exception: $exception")
            Result.failure()
        }
    }
}