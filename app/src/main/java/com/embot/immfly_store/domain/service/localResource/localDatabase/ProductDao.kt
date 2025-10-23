package com.embot.immfly_store.domain.service.localResource.localDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity


@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM products")
    fun getCartProducts(): List<ProductEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeToCart(productEntry: ProductEntity)

    @Transaction
    @Delete(entity = ProductEntity::class)
    fun deleteFromCart(productEntry: ProductEntity)

    @Transaction
    @Query("Update products set quantity = :quantity where id = :id")
    fun updateProductQuantity(id: String, quantity: Int)

}