package com.nikitadevktad.vkeducation.ui.appdetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nikitadevktad.vkeducation.data.AppInfo
import com.nikitadevktad.vkeducation.domain.appdetails.AppDetails
import com.nikitadevktad.vkeducation.domain.appdetails.Category
import com.nikitadevktad.vkeducation.domain.appdetails.SimilarApp

/**
 * Экран практики: исходный AppDetailsContent и дочерние composable-компоненты
 * переиспользованы без Hilt/Retrofit, поскольку в ДЗ данные захардкожены.
 */
@Composable
fun AppDetailsScreen(
    app: AppInfo,
    allApps: List<AppInfo>,
    showcaseApps: List<AppInfo>,
    onBackClick: () -> Unit,
    onShowcaseAppClick: () -> Unit,
) {
    var descriptionExpanded by rememberSaveable(app.name) { mutableStateOf(false) }

    val details = remember(app) {
        AppDetails(
            id = app.name,
            name = app.name,
            developer = "VK Education",
            category = app.category.toAppCategory(),
            ageRating = 0,
            size = 42.0f,
            iconUrl = app.iconUrl,
            screenshotUrlList = listOf(
                "https://picsum.photos/id/1018/800/450",
                "https://picsum.photos/id/1015/800/450",
                "https://picsum.photos/id/1019/800/450",
            ),
            description = app.description,
        )
    }

    val similarApps = remember(app, allApps) {
        allApps.filter { it.name != app.name }.take(5).map { other ->
            SimilarApp(id = other.name, name = other.name, iconUrl = other.iconUrl)
        }
    }

    Scaffold { padding ->
        AppDetailsContent(
            content = AppDetailsState.Content(
                appDetails = details,
                similarApps = similarApps,
                descriptionCollapsed = descriptionExpanded,
            ),
            onBackClick = onBackClick,
            onShareClick = {},
            onInstallClick = {},
            onReadMoreClick = { descriptionExpanded = true },
            onDeveloperClick = {},
            onSimilarAppDownloadClick = {},
            showcaseApps = showcaseApps,
            onShowcaseAppClick = onShowcaseAppClick,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(padding),
        )
    }
}

private fun String.toAppCategory(): Category = when (this) {
    "Производительность" -> Category.PRODUCTIVITY
    "Спорт", "Здоровье и спорт" -> Category.SPORTS
    "Фотография" -> Category.PHOTOGRAPHY
    "Еда" -> Category.FOOD
    "Образование" -> Category.EDUCATION
    "Здоровье" -> Category.HEALTH
    "Покупки" -> Category.SHOPPING
    "Социальные сети" -> Category.SOCIAL
    "Утилиты" -> Category.UTILITIES
    "Музыка" -> Category.MUSIC
    else -> Category.APP
}
