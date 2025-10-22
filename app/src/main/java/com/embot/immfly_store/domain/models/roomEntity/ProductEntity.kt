package com.embot.immfly_store.domain.models.roomEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "stock") val stock: Int
)