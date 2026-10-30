package com.nikitadevktad.vkeducation.domain.usecase

import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.domain.repository.AppRepository

class GetAppsUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke(): List<App> = repository.getApps()
}
