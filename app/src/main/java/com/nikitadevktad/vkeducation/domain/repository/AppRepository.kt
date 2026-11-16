package com.nikitadevktad.vkeducation.domain.repository

import com.nikitadevktad.vkeducation.domain.model.App

interface AppRepository {
    fun getApps(): List<App>

    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> = emptyMap()

    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = emptyMap()
}
