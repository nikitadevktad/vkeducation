package com.nikitadevktad.vkeducation.data.repository

import com.nikitadevktad.vkeducation.data.mapper.toDomain
import com.nikitadevktad.vkeducation.data.remote.CatalogApi
import com.nikitadevktad.vkeducation.domain.model.ApplicationDetails
import com.nikitadevktad.vkeducation.domain.model.CatalogApplication
import com.nikitadevktad.vkeducation.domain.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
) : CatalogRepository {
    override suspend fun getCatalog(): List<CatalogApplication> {
        return api.getCatalog().map { it.toDomain() }
    }

    override suspend fun getApplication(id: String): ApplicationDetails =
        api.getApplication(id).toDomain()
}
