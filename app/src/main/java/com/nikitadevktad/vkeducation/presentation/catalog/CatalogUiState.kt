package com.nikitadevktad.vkeducation.presentation.catalog

import com.nikitadevktad.vkeducation.domain.model.CatalogApplication

sealed interface CatalogUiState {
    data object Loading : CatalogUiState
    data class Success(val applications: List<CatalogApplication>) : CatalogUiState
    data object Empty : CatalogUiState
    data class Error(val message: String) : CatalogUiState
}
