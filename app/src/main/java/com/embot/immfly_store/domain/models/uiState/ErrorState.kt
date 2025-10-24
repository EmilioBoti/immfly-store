package com.embot.immfly_store.domain.models.uiState

sealed class ErrorState {
    data object NetworkError: ErrorState()
    data object UnknownError: ErrorState()
    data object None: ErrorState()
}
