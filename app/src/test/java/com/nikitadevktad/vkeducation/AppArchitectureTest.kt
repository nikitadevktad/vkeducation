package com.nikitadevktad.vkeducation

import com.nikitadevktad.vkeducation.data.dto.AppDto
import com.nikitadevktad.vkeducation.data.mapper.AppMapper
import com.nikitadevktad.vkeducation.data.repository.AppRepositoryImpl
import com.nikitadevktad.vkeducation.data.source.AppLocalDataSource
import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.domain.repository.AppRepository
import com.nikitadevktad.vkeducation.domain.usecase.GetAppsUseCase
import com.nikitadevktad.vkeducation.presentation.applist.AppListUiState
import com.nikitadevktad.vkeducation.presentation.applist.AppListViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppArchitectureTest {
    @Test
    fun mapper_convertsDtoToDomainModel() {
        val dto = AppDto("Name", "Description", "Category", "url")

        assertEquals(App("Name", "Description", "Category", "url"), AppMapper().toDomain(dto))
    }

    @Test
    fun repository_returnsMappedLocalApps() {
        val apps = AppRepositoryImpl(AppLocalDataSource(), AppMapper()).getApps()

        assertEquals(15, apps.size)
        assertEquals("QuickNote", apps.first().name)
    }

    @Test
    fun useCase_delegatesToRepository() {
        val expected = listOf(App("Name", "Description", "Category", "url"))
        val repository = object : AppRepository {
            override fun getApps(): List<App> = expected
        }

        assertEquals(expected, GetAppsUseCase(repository)())
    }

    @Test
    fun viewModel_exposesAppsAsReadOnlyUiState() {
        val repository = object : AppRepository {
            override fun getApps(): List<App> = listOf(App("Name", "Description", "Category", "url"))
        }

        val state = AppListViewModel(GetAppsUseCase(repository)).uiState.value

        assertTrue(state is AppListUiState.Success)
        assertEquals(1, (state as AppListUiState.Success).apps.size)
    }
}
