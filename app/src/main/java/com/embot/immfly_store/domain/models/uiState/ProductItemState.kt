package com.embot.immfly_store.domain.models.uiState

import com.embot.immfly_store.domain.models.constants.CurrencyType


data class ProductItemState(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val stock: Int,
    val isBucketed: Boolean,
    val currencyType: CurrencyType
)
