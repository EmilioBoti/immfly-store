package com.embot.immfly_store.domain.models.appModel


class ProductModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double,
    val imageUrl: String = "",
    val stock: Int = -1
) {

}