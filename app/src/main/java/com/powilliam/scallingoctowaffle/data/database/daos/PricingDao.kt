package com.powilliam.scallingoctowaffle.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.powilliam.scallingoctowaffle.data.entities.Pricing
import kotlinx.coroutines.flow.Flow

@Dao
interface PricingDao {
    @Query("SELECT * FROM pricing WHERE pricing_product_id = :productId")
    fun allByProductId(productId: Int): Flow<List<Pricing>>

    @Insert
    suspend fun insert(vararg pricing: Pricing)
}