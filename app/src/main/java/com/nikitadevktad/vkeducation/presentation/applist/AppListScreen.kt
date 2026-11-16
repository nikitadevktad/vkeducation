package com.nikitadevktad.vkeducation.presentation.applist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikitadevktad.vkeducation.R
import com.nikitadevktad.vkeducation.domain.model.App

@Composable
fun AppListRoute(
    viewModel: AppListViewModel,
    onAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val state = viewModel.uiState.collectAsStateWithLifecycle().value) {
        AppListUiState.Loading -> Unit
        is AppListUiState.Success -> AppListScreen(
            apps = state.apps,
            ratings = state.ratings,
            ratingsLoading = state.ratingsLoading,
            ratingLoadMode = state.ratingLoadMode,
            ratingLoadTimeMillis = state.ratingLoadTimeMillis,
            onLoadSequentially = viewModel::loadRatingsSequentially,
            onLoadInParallel = viewModel::loadRatingsInParallel,
            onAppClick = onAppClick,
            modifier = modifier,
        )
        is AppListUiState.Error -> Text(text = state.message, modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    apps: List<App>,
    ratings: Map<String, Float>,
    ratingsLoading: Boolean,
    ratingLoadMode: RatingLoadMode?,
    ratingLoadTimeMillis: Long?,
    onLoadSequentially: () -> Unit,
    onLoadInParallel: () -> Unit,
    onAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Список приложений")
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.icon_main),
                        contentDescription = "Иконка приложения",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            RatingControls(
                loading = ratingsLoading,
                mode = ratingLoadMode,
                elapsedMillis = ratingLoadTimeMillis,
                onLoadSequentially = onLoadSequentially,
                onLoadInParallel = onLoadInParallel,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(apps, key = App::id) { item ->
                    AppItem(
                        appData = item,
                        rating = ratings[item.id],
                        onClick = { onAppClick(item) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingControls(
    loading: Boolean,
    mode: RatingLoadMode?,
    elapsedMillis: Long?,
    onLoadSequentially: () -> Unit,
    onLoadInParallel: () -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onLoadSequentially, enabled = !loading) { Text("Последовательно") }
            Button(onClick = onLoadInParallel, enabled = !loading) { Text("Параллельно") }
            if (loading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }
        if (!loading && mode != null && elapsedMillis != null) {
            Text("${mode.title}: $elapsedMillis мс")
        }
    }
}
