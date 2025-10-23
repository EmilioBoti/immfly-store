package com.embot.immfly_store.domain.models.uiState

import kotlin.String

data class CartItemState(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val realPrice: Double,
    val quantity: Int,
    val stock: Int,
)

data class DisplayActionState(
    val isOpen: Boolean,
    val actionCartState: ActionCartState?
)

sealed class ActionCartState {
    data object ConfirmDelete: ActionCartState()
    data object DeleteSuccess: ActionCartState()
    data class CartError(
        val isError: Boolean,
        val message: String
    ): ActionCartState()
}