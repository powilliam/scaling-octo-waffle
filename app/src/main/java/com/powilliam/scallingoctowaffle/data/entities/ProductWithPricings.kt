package com.powilliam.scallingoctowaffle.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithPricings(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "pricing_product_id"
    )
    val pricings: List<Pricing>
)
