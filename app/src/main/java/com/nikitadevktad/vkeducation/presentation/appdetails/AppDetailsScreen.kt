package com.nikitadevktad.vkeducation.presentation.appdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikitadevktad.vkeducation.R
import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.Category
import com.nikitadevktad.vkeducation.domain.appdetails.SimilarApp
import kotlinx.coroutines.launch

@Composable
fun AppDetailsScreen(
    appInfo: App,
    appIndex: Int,
    allApps: List<App>,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: AppDetailsViewModel = hiltViewModel()
    val appDetails = remember(appInfo, appIndex) {
        appInfo.toAppDetails(appIndex)
    }
    val similarApps = remember(allApps, appIndex) {
        allApps.toSimilarApps(selectedAppIndex = appIndex)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val underDevelopmentText = stringResource(R.string.under_development)
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(appDetails.id, similarApps) {
        viewModel.setApp(appDetails, similarApps)
    }

    fun showUnderDevelopmentMessage() {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(underDevelopmentText)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
    ) { contentPadding ->
        val content = state as? AppDetailsState.Content
        if (content != null) AppDetailsContent(
            content = content,
            showcaseApps = allApps,
            onBackClick = onBackClick,
            onShareClick = ::showUnderDevelopmentMessage,
            onInstallClick = viewModel::onInstallClick,
            onReadMoreClick = viewModel::onReadMoreClick,
            onDeveloperClick = ::showUnderDevelopmentMessage,
            onSimilarAppDownloadClick = {
                showUnderDevelopmentMessage()
            },
            onShowcaseAppClick = onShowcaseAppClick,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(contentPadding),
        )
    }
}

private fun App.toAppDetails(index: Int): AppDetails = AppDetails(
    id = id,
    name = name,
    developer = "VK Education",
    category = category.toCategory(),
    ageRating = 12,
    size = 120f + index,
    iconUrl = iconUrl,
    screenshotUrlList = demoScreenshotUrlList,
    description = description,
)

private fun List<App>.toSimilarApps(selectedAppIndex: Int): List<SimilarApp> =
    mapIndexedNotNull { index, appInfo ->
        if (index == selectedAppIndex) {
            null
        } else {
            SimilarApp(
                id = appInfo.id,
                name = appInfo.name,
                iconUrl = appInfo.iconUrl,
            )
        }
    }.take(5)

private fun String.toCategory(): Category = when (this) {
    "Приложения" -> Category.APP
    "Игры" -> Category.GAME
    "Производительность" -> Category.PRODUCTIVITY
    "Социальные сети" -> Category.SOCIAL
    "Образование" -> Category.EDUCATION
    "Развлечения" -> Category.ENTERTAINMENT
    "Музыка" -> Category.MUSIC
    "Видео" -> Category.VIDEO
    "Фотография" -> Category.PHOTOGRAPHY
    "Здоровье" -> Category.HEALTH
    "Здоровье и спорт" -> Category.HEALTH_AND_SPORTS
    "Спорт" -> Category.SPORTS
    "Новости" -> Category.NEWS
    "Книги" -> Category.BOOKS
    "Бизнес" -> Category.BUSINESS
    "Финансы" -> Category.FINANCE
    "Путешествия" -> Category.TRAVEL
    "Карты" -> Category.MAPS
    "Еда" -> Category.FOOD
    "Покупки" -> Category.SHOPPING
    "Утилиты" -> Category.UTILITIES
    else -> Category.APP
}

private val demoScreenshotUrlList = listOf(
    "https://picsum.photos/id/1015/1280/720",
    "https://picsum.photos/id/1016/1280/720",
    "https://picsum.photos/id/1018/1280/720",
)
