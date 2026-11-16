package com.nikitadevktad.vkeducation.presentation.appdetails

import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppDetailsViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `installation progresses completes and does not restart`() = runTest(dispatcher) {
        val viewModel = AppDetailsViewModel().apply { setApp(details, emptyList()) }
        assertEquals(InstallState.NotInstalled, content(viewModel).installState)

        viewModel.onInstallClick()
        assertEquals(InstallState.Installing(0), content(viewModel).installState)
        testScheduler.advanceTimeBy(150)
        runCurrent()
        assertEquals(InstallState.Installing(5), content(viewModel).installState)

        testScheduler.advanceUntilIdle()
        assertEquals(InstallState.Installed, content(viewModel).installState)
        viewModel.onInstallClick()
        assertEquals(InstallState.Installed, content(viewModel).installState)
    }

    @Test
    fun `second click cancels installation and progress stops`() = runTest(dispatcher) {
        val viewModel = AppDetailsViewModel().apply { setApp(details, emptyList()) }
        viewModel.onInstallClick()
        testScheduler.advanceTimeBy(300)
        runCurrent()
        viewModel.onInstallClick()

        assertEquals(InstallState.NotInstalled, content(viewModel).installState)
        testScheduler.advanceUntilIdle()
        assertEquals(InstallState.NotInstalled, content(viewModel).installState)
    }

    private fun content(viewModel: AppDetailsViewModel): AppDetailsState.Content =
        viewModel.state.value as AppDetailsState.Content

    private companion object {
        val details = AppDetails(
            id = "test-app",
            name = "Test",
            developer = "VK Education",
            category = Category.APP,
            ageRating = 12,
            size = 1f,
            iconUrl = "",
            screenshotUrlList = emptyList(),
            description = "Description",
        )
    }
}
