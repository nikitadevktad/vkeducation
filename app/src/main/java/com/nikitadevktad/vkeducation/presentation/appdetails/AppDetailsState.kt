package com.nikitadevktad.vkeducation.presentation.appdetails

import androidx.compose.runtime.Immutable
import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.SimilarApp

@Immutable
sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data object Error : AppDetailsState
    data class Content(
        val appDetails: AppDetails,
        val similarApps: List<SimilarApp>,
        val descriptionCollapsed: Boolean,
    ) : AppDetailsState
}
