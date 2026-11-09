package com.nikitadevktad.vkeducation.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikitadevktad.vkeducation.domain.model.CatalogApplication

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        CatalogUiState.Loading -> LoadingContent(modifier)
        CatalogUiState.Empty -> MessageContent(
            message = "Каталог пуст",
            modifier = modifier,
        )
        is CatalogUiState.Error -> ErrorContent(
            message = uiState.message,
            onRetry = onRetry,
            modifier = modifier,
        )
        is CatalogUiState.Success -> CatalogContent(
            applications = uiState.applications,
            modifier = modifier,
        )
    }
}

@Composable
private fun CatalogContent(applications: List<CatalogApplication>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(applications) { application ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = application.name, style = MaterialTheme.typography.titleMedium)
                Text(text = application.category, style = MaterialTheme.typography.labelLarge)
                Text(text = application.description, style = MaterialTheme.typography.bodyMedium)
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MessageContent(message: String, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = message)
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = message)
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text(text = "Повторить")
        }
    }
}
