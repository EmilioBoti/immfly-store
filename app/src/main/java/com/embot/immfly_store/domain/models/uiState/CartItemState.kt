package com.embot.immfly_store.domain.models.uiState

import kotlin.String

data class CartItemState(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val realPrice: Double,
    val cuantity: Int,
    val stock: Int,
)
