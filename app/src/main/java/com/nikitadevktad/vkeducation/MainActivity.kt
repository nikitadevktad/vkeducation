package com.nikitadevktad.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikitadevktad.vkeducation.data.apps
import com.nikitadevktad.vkeducation.ui.appdetails.AppDetailsScreen
import com.nikitadevktad.vkeducation.ui.applist.AppListScreen
import com.nikitadevktad.vkeducation.ui.theme.VkEducationTheme

private const val AppsRoute = "apps"
private const val AppIndexArgument = "appIndex"
private const val DetailsRoute = "details/{$AppIndexArgument}"

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
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppsRoute,
    ) {
        composable(AppsRoute) {
            AppListScreen(
                apps = apps,
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
