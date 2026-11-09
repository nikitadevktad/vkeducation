package com.nikitadevktad.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.nikitadevktad.vkeducation.presentation.catalog.CatalogScreen
import com.nikitadevktad.vkeducation.presentation.catalog.CatalogViewModel
import com.nikitadevktad.vkeducation.ui.theme.VkEducationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                val catalogViewModel: CatalogViewModel = hiltViewModel()
                val uiState = catalogViewModel.uiState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatalogScreen(
                        uiState = uiState.value,
                        onRetry = catalogViewModel::loadCatalog,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
