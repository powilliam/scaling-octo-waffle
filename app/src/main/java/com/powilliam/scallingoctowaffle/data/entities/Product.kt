package com.powilliam.scallingoctowaffle.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val id: Int = 1,
    @ColumnInfo(name = "product_name")
    val name: String,
    @ColumnInfo(name = "product_href")
    val href: String,
) {
    companion object {
        fun forTesting(id: Int = 1, name: String) = Product(
            id = id,
            name = name,
            href = "https://ecommerce.com/$id"
        )
    }
}