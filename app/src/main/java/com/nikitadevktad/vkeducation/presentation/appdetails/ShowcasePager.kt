package com.nikitadevktad.vkeducation.presentation.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikitadevktad.vkeducation.R
import com.nikitadevktad.vkeducation.domain.model.App
import com.nikitadevktad.vkeducation.presentation.applist.AppItem

private const val ShowcasePageCount = 3
private const val AppsPerPage = 3
private const val ShowcaseAppCount = ShowcasePageCount * AppsPerPage

@Composable
fun ShowcasePager(
    apps: List<App>,
    onAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
) {
    val showcasePages = apps
        .take(ShowcaseAppCount)
        .chunked(AppsPerPage)
    val pagerState = rememberPagerState(pageCount = { ShowcasePageCount })

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.showcase_title),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(8.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(406.dp),
        ) { page ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                showcasePages[page].forEach { app ->
                    AppItem(
                        appData = app,
                        onClick = { onAppClick(app) },
                    )
                }
            }
        }
    }
}
