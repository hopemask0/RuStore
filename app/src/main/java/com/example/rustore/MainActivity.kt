package com.example.rustore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rustore.presentation.navigation.RuStoreNavGraph
import com.example.rustore.presentation.screens.appdetail.AppDetailScreen
import com.example.rustore.presentation.screens.onboarding.OnboardingScreen
import com.example.rustore.presentation.screens.applist.AppListScreen
import com.example.rustore.presentation.theme.RuStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuStoreTheme {
                RuStoreNavGraph() // ← Используем навигационный граф
            }
        }
    }
}

@Composable
fun RuStoreApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onContinue = {
                    navController.navigate("appList") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        composable("appList") {
            AppListScreen(
                onAppClick = { appId ->
                    navController.navigate("appDetail/$appId")
                },
                onCategoriesClick = {
                    // Пока заглушка - реализуем позже
                }
            )
        }
        composable(
            route = "appDetail/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            AppDetailScreen(
                appId = appId,
                onBack = { navController.popBackStack() },
                onScreenshotClick = { index ->
                    // Реализуем позже
                }
            )
        }
    }
}