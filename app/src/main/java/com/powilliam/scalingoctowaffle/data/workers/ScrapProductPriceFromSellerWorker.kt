package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.repositories.HyperTextsRepository
import com.powilliam.scalingoctowaffle.data.repositories.PricingsRepository
import com.powilliam.scalingoctowaffle.data.repositories.SellersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ScrapProductPriceFromSellerWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val hyperTexts: HyperTextsRepository,
    private val sellers: SellersRepository,
    private val pricings: PricingsRepository,
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

            val price = text.substring(seller.substringStartsAt.toInt()).toDouble()
            val pricing = Pricing(productId = productId, inCents = price * 100)

            pricings.insert(pricing)

            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}