package com.embot.immfly_store.domain.models.uiState


data class ProductItemState(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val rawPrice: Double,
    val currencies: List<String>,
    val imageUrl: String,
    val stock: Int,
    val isBucketed: Boolean
)
