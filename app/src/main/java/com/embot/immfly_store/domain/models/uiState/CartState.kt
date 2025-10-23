package com.embot.immfly_store.domain.models.uiState



data class CartState(
    val totalPrice: String,
    val amount: Double,
    val products: List<CartItemState>
)