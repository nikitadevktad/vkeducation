package com.nikitadevktad.vkeducation.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.vkeducation.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: CatalogRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            _uiState.value = runCatching { repository.getCatalog() }
                .fold(
                    onSuccess = { applications ->
                        if (applications.isEmpty()) CatalogUiState.Empty
                        else CatalogUiState.Success(applications)
                    },
                    onFailure = { error ->
                        CatalogUiState.Error(error.message ?: "Не удалось загрузить каталог")
                    },
                )
        }
    }
}
