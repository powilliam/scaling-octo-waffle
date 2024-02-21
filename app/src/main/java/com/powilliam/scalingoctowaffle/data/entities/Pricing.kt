package com.powilliam.scalingoctowaffle.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Entity
data class Pricing(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pricing_id")
    val id: Int = 1,
    @ColumnInfo(name = "pricing_product_id")
    val productId: Int,
    @ColumnInfo(name = "pricing_in_cents")
    val inCents: Double,
    @ColumnInfo(name = "pricing_created_at")
    val createdAt: Long = System.currentTimeMillis(),
) {
    companion object {
        fun forTesting(id: Int = 1, productId: Int) = Pricing(
            id = id,
            productId = productId,
            inCents = 1500.0
        )
    }
}

fun Pricing.date(): String {
    val formatter = SimpleDateFormat("EEE, d' 'MMM yyyy", Locale.getDefault())
    val date = Date.from(Instant.ofEpochMilli(createdAt))

    return formatter.format(date)
}

fun Pricing.currency(): String = NumberFormat.getCurrencyInstance().format(inCents / 100)
