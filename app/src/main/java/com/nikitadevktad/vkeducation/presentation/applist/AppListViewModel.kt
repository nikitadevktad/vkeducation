package com.nikitadevktad.vkeducation.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitadevktad.vkeducation.domain.usecase.GetAppsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel(
    private val getAppsUseCase: GetAppsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _uiState.value = runCatching { getAppsUseCase() }
            .fold(
                onSuccess = { AppListUiState.Success(it) },
                onFailure = { AppListUiState.Error(it.message ?: "Unable to load apps") },
            )
    }

    companion object {
        fun factory(getAppsUseCase: GetAppsUseCase): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    AppListViewModel(getAppsUseCase) as T
            }
    }
}
