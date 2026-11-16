package com.nikitadevktad.vkeducation.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.vkeducation.domain.repository.AppRepository
import com.nikitadevktad.vkeducation.domain.model.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()
    private var ratingsJob: Job? = null

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

    fun loadRatingsSequentially() = loadRatings(RatingLoadMode.Sequential) { ids ->
        repository.loadRatingsSequentially(ids)
    }

    fun loadRatingsInParallel() = loadRatings(RatingLoadMode.Parallel) { ids ->
        repository.loadRatingsInParallel(ids)
    }

    private fun loadRatings(
        mode: RatingLoadMode,
        loader: suspend (List<String>) -> Map<String, Float>,
    ) {
        if (ratingsJob?.isActive == true) return
        val current = _uiState.value as? AppListUiState.Success ?: return
        _uiState.value = current.copy(ratingsLoading = true, ratingLoadMode = mode, ratingLoadError = null)
        ratingsJob = viewModelScope.launch {
            try {
                val (ratings, elapsedMillis) = measureTimeMillis {
                    loader(current.apps.map(App::id))
                }
                _uiState.update { state ->
                    (state as? AppListUiState.Success)?.copy(
                        ratings = ratings,
                        ratingsLoading = false,
                        ratingLoadMode = mode,
                        ratingLoadTimeMillis = elapsedMillis,
                    ) ?: state
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update { state ->
                    (state as? AppListUiState.Success)?.copy(
                        ratingsLoading = false,
                        ratingLoadError = exception.message ?: "Unable to load ratings",
                    ) ?: state
                }
            } finally {
                ratingsJob = null
            }
        }
    }

    private suspend fun <T> measureTimeMillis(block: suspend () -> T): Pair<T, Long> {
        val startedAt = System.nanoTime()
        val result = block()
        return result to (System.nanoTime() - startedAt) / 1_000_000
    }
}
