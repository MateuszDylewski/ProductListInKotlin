package com.example.productlist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductDB: RoomDatabase() {

    abstract fun getProductDao(): ProductDAO

    companion object{
        var instance: ProductDB? = null

        fun getDatabase(context: Context): ProductDB? {
            if (instance != null) {
                return instance
            }

            instance = Room.databaseBuilder(
                context,
                ProductDB::class.java,
                "ProductDB"
            ).build()

            return instance
        }
    }

}