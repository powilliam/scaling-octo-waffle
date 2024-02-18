package com.powilliam.scalingoctowaffle.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Transaction
    @Query("SELECT * FROM product")
    fun allWithPricings(): Flow<List<ProductWithPricings>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)
}