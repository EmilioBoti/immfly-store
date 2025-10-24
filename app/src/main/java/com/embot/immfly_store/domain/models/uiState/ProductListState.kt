package com.embot.immfly_store.domain.models.uiState

data class ProductListState(
    val error: GenericState,
    val products: List<ProductItemState> = listOf()
)
