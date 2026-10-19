package com.nikitadevktad.vkeducation.ui.appdetails

import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.SimilarApp

// Сохраняем контракт контента из практического репозитория.
sealed interface AppDetailsState {
    data class Content(
        val appDetails: AppDetails,
        val similarApps: List<SimilarApp>,
        val descriptionCollapsed: Boolean,
    ) : AppDetailsState
}
