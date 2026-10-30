package com.nikitadevktad.vkeducation.presentation.applist

import androidx.compose.runtime.Immutable
import com.nikitadevktad.vkeducation.domain.model.App

@Immutable
sealed interface AppListUiState {
    data object Loading : AppListUiState
    data class Success(val apps: List<App>) : AppListUiState
    data class Error(val message: String) : AppListUiState
}
