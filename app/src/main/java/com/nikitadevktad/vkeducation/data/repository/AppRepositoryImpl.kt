package com.nikitadevktad.vkeducation.data.repository

import com.nikitadevktad.vkeducation.data.mapper.AppMapper
import com.nikitadevktad.vkeducation.data.source.AppLocalDataSource
import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.domain.repository.AppRepository

class AppRepositoryImpl(
    private val localDataSource: AppLocalDataSource,
    private val mapper: AppMapper,
) : AppRepository {
    override fun getApps(): List<App> = localDataSource.getApps().map(mapper::toDomain)
}
