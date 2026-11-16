package com.nikitadevktad.vkeducation.presentation.appdetails

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState {
        init {
            require(progress in 0..100)
        }
    }
    data object Installed : InstallState
}
