package com.nikitadevktad.vkeducation.presentation.applist

import androidx.lifecycle.ViewModel
import com.nikitadevktad.vkeducation.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _uiState.value = runCatching { repository.getApps() }
            .fold(
                onSuccess = { AppListUiState.Success(it) },
                onFailure = { AppListUiState.Error(it.message ?: "Unable to load apps") },
            )
    }
}
