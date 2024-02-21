package com.powilliam.scalingoctowaffle.data.sources.remote

import com.google.firebase.database.FirebaseDatabase
import com.powilliam.scalingoctowaffle.data.entities.Seller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SellersRemoteDataSource {
    suspend fun byHost(host: String): Seller?
}

class SellersRemoteDataSourceImpl @Inject constructor(
    private val database: FirebaseDatabase
) : SellersRemoteDataSource {
    override suspend fun byHost(host: String): Seller? = withContext(Dispatchers.IO) {
        val sellers = database.reference.child("sellers")
            .get()
            .await()
            .value as Map<String, Map<String, *>>? ?: return@withContext null

        val seller = sellers.values.firstOrNull { it.containsValue(host) } ?: return@withContext null
        val cast = seller as Map<*, *>
        val configuration = cast["price_configuration"] as Map<*, *>?

        Seller(
            host = cast["host"] as String,
            inDocumentPriceReference = cast["in_document_price_reference"] as String,
            replaceAll = configuration?.get("replace_all") as Map<String, String>? ?: mapOf(),
            substringStartsAt = configuration?.get("substring_starts_at") as Long? ?: 0
        );
    }
}