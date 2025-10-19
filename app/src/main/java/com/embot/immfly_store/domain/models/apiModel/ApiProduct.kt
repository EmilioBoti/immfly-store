package com.embot.immfly_store.domain.models.apiModel

import com.google.gson.annotations.SerializedName


data class ApiProduct(
    @SerializedName("id")  val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String,
    @SerializedName("stock") val stock: Int
)
