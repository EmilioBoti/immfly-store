package com.embot.immfly_store.domain.models.uiState

data class GenericState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorState = ErrorState.None
)