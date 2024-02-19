package com.powilliam.scallingoctowaffle.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.powilliam.scallingoctowaffle.data.database.daos.PricingDao
import com.powilliam.scallingoctowaffle.data.database.daos.ProductDao
import com.powilliam.scallingoctowaffle.data.entities.Pricing
import com.powilliam.scallingoctowaffle.data.entities.Product

@Database(entities = [Product::class, Pricing::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun product(): ProductDao
    abstract fun pricing(): PricingDao
}