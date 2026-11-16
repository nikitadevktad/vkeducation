package com.nikitadevktad.vkeducation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.SimilarApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AppDetailsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var installJob: Job? = null
    private var installationGeneration = 0L

    fun setApp(appDetails: AppDetails, similarApps: List<SimilarApp>) {
        val current = _state.value as? AppDetailsState.Content
        if (current?.appDetails?.id != appDetails.id) {
            installJob?.cancel()
            installJob = null
            installationGeneration++
            _state.value = AppDetailsState.Content(
                appDetails = appDetails,
                similarApps = similarApps,
                descriptionCollapsed = false,
            )
        }
    }

    fun onReadMoreClick() {
        _state.update { current ->
            (current as? AppDetailsState.Content)?.copy(descriptionCollapsed = true) ?: current
        }
    }

    fun onInstallClick() {
        val content = _state.value as? AppDetailsState.Content ?: return
        when (content.installState) {
            InstallState.NotInstalled -> {
                installationGeneration++
                _state.value = content.copy(installState = InstallState.Installing(0))
                installJob = viewModelScope.launch { emulateInstall() }
            }
            is InstallState.Installing -> {
                installationGeneration++
                installJob?.cancel()
                installJob = null
                _state.update { current ->
                    (current as? AppDetailsState.Content)
                        ?.copy(installState = InstallState.NotInstalled) ?: current
                }
            }
            InstallState.Installed -> Unit
        }
    }

    private suspend fun emulateInstall() {
        val generation = installationGeneration
        for (progress in 5..100 step 5) {
            delay(150)
            if (generation != installationGeneration) return
            _state.update { current ->
                val content = current as? AppDetailsState.Content ?: return@update current
                if (content.installState is InstallState.Installing) {
                    content.copy(installState = InstallState.Installing(progress))
                } else {
                    content
                }
            }
        }
        if (generation == installationGeneration) {
            _state.update { current ->
                val content = current as? AppDetailsState.Content ?: return@update current
                content.copy(installState = InstallState.Installed)
            }
        }
    }

    override fun onCleared() {
        installJob?.cancel()
        super.onCleared()
    }
}
