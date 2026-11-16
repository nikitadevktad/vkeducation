package com.nikitadevktad.vkeducation.presentation.applist

import androidx.compose.runtime.Immutable
import com.nikitadevktad.vkeducation.domain.model.App

@Immutable
sealed interface AppListUiState {
    data object Loading : AppListUiState
    data class Success(
        val apps: List<App>,
        val ratings: Map<String, Float> = emptyMap(),
        val ratingsLoading: Boolean = false,
        val ratingLoadMode: RatingLoadMode? = null,
        val ratingLoadTimeMillis: Long? = null,
        val ratingLoadError: String? = null,
    ) : AppListUiState
    data class Error(val message: String) : AppListUiState
}

enum class RatingLoadMode(val title: String) {
    Sequential("Последовательно"),
    Parallel("Параллельно"),
}
