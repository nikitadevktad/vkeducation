package com.nikitadevktad.vkeducation.domain.repository

import com.nikitadevktad.vkeducation.domain.model.CatalogApplication
import com.nikitadevktad.vkeducation.domain.model.ApplicationDetails

interface CatalogRepository {
    suspend fun getCatalog(): List<CatalogApplication>

    suspend fun getApplication(id: String): ApplicationDetails
}
