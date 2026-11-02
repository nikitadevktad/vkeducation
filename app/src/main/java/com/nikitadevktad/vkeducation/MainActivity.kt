package com.nikitadevktad.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import com.nikitadevktad.vkeducation.presentation.appdetails.AppDetailsScreen
import com.nikitadevktad.vkeducation.presentation.applist.AppListRoute
import com.nikitadevktad.vkeducation.presentation.applist.AppListUiState
import com.nikitadevktad.vkeducation.presentation.applist.AppListViewModel
import com.nikitadevktad.vkeducation.presentation.theme.VkEducationTheme

private const val AppsRoute = "apps"
private const val AppIndexArgument = "appIndex"
private const val DetailsRoute = "details/{$AppIndexArgument}"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                VkEducationNavHost()
            }
        }
    }
}

@Composable
private fun VkEducationNavHost() {
    val appListViewModel = hiltViewModel<AppListViewModel>()
    val navController = rememberNavController()
    val uiState = appListViewModel.uiState.collectAsStateWithLifecycle().value
    val apps = (uiState as? AppListUiState.Success)?.apps.orEmpty()

    NavHost(
        navController = navController,
        startDestination = AppsRoute,
    ) {
        composable(AppsRoute) {
            AppListRoute(
                viewModel = appListViewModel,
                onAppClick = { appInfo ->
                    val appIndex = apps.indexOf(appInfo)
                    if (appIndex >= 0) {
                        navController.navigate("details/$appIndex")
                    }
                },
            )
        }
        composable(
            route = DetailsRoute,
            arguments = listOf(
                navArgument(AppIndexArgument) {
                    type = NavType.IntType
                }
            ),
        ) { backStackEntry ->
            val appIndex = backStackEntry.arguments?.getInt(AppIndexArgument) ?: -1
            val appInfo = apps.getOrNull(appIndex)

            if (appInfo == null) {
                LaunchedEffect(backStackEntry) {
                    navController.popBackStack()
                }
            } else {
                AppDetailsScreen(
                    appInfo = appInfo,
                    appIndex = appIndex,
                    allApps = apps,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onShowcaseAppClick = {
                        navController.popBackStack(
                            route = AppsRoute,
                            inclusive = false,
                        )
                    },
                )
            }
        }
    }
}
