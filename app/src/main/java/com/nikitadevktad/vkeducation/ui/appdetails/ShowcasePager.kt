package com.nikitadevktad.vkeducation.ui.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nikitadevktad.vkeducation.data.AppInfo
import com.nikitadevktad.vkeducation.ui.applist.AppItem

/**
 * Три фиксированные страницы, на каждой по три приложения.
 * Переиспользуем ту же карточку AppItem, что и в общем списке.
 */
@Composable
fun ShowcasePager(
    apps: List<AppInfo>,
    onAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    require(apps.size == 9) { "Витрина должна содержать ровно 9 приложений" }
    val pages = remember(apps) { apps.chunked(3) }
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(modifier = modifier) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(12.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(406.dp),
        ) { pageIndex ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                pages[pageIndex].forEach { app ->
                    AppItem(
                        appData = app,
                        onClick = onAppClick,
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = (pagerState.currentPage + 1).toString() + " / 3",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
