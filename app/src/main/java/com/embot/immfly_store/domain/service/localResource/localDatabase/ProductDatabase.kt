package com.embot.immfly_store.domain.service.localResource.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity


@Database(
    entities = [
        ProductEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabaseInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "immfly_store_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    instance
                }
            }
        }

    }


}