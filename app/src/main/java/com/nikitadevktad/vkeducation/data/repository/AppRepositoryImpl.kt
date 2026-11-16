package com.nikitadevktad.vkeducation.data.repository

import com.nikitadevktad.vkeducation.data.mapper.AppMapper
import com.nikitadevktad.vkeducation.data.source.AppLocalDataSource
import com.nikitadevktad.vkeducation.data.source.AppApi
import com.nikitadevktad.vkeducation.data.source.FakeAppApi
import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.domain.repository.AppRepository
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class AppRepositoryImpl @Inject constructor(
    private val localDataSource: AppLocalDataSource,
    private val mapper: AppMapper,
    private val appApi: AppApi,
) : AppRepository {
    constructor(
        localDataSource: AppLocalDataSource,
        mapper: AppMapper,
    ) : this(localDataSource, mapper, FakeAppApi())

    override fun getApps(): List<App> = localDataSource.getApps().map(mapper::toDomain)

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> = buildMap {
        ids.forEach { id -> put(id, appApi.getRating(id)) }
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id -> async { id to appApi.getRating(id) } }.awaitAll().toMap()
    }
}
