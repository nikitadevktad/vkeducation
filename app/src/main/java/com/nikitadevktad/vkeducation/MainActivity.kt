package com.nikitadevktad.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikitadevktad.vkeducation.data.apps
import com.nikitadevktad.vkeducation.ui.appdetails.AppDetailsScreen
import com.nikitadevktad.vkeducation.ui.applist.AppListScreen
import com.nikitadevktad.vkeducation.ui.theme.VkEducationTheme

private const val APPS_ROUTE = "apps"
private const val DETAILS_ROUTE = "details/{appIndex}"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = APPS_ROUTE) {
        composable(APPS_ROUTE) {
            AppListScreen(
                apps = apps,
                onAppClick = { app ->
                    val index = apps.indexOf(app)
                    if (index >= 0) navController.navigate("details/" + index)
                },
            )
        }
        composable(
            route = DETAILS_ROUTE,
            arguments = listOf(navArgument("appIndex") { type = NavType.IntType }),
        ) { entry ->
            val app = apps.getOrNull(entry.arguments?.getInt("appIndex") ?: -1)
            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    allApps = apps,
                    showcaseApps = apps.take(9),
                    onBackClick = { navController.popBackStack() },
                    // Из любого элемента витрины возвращаемся на существующий список.
                    onShowcaseAppClick = {
                        navController.popBackStack(APPS_ROUTE, inclusive = false)
                    },
                )
            }
        }
    }
}
