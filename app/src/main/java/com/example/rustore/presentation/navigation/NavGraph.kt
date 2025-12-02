package com.example.rustore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rustore.presentation.screens.appdetail.AppDetailScreen
import com.example.rustore.presentation.screens.applist.AppListScreen
import com.example.rustore.presentation.screens.categories.CategoriesScreen
import com.example.rustore.presentation.screens.onboarding.OnboardingScreen
import com.example.rustore.presentation.screens.screenshots.ScreenshotsScreen

@Composable
fun RuStoreNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onContinue = {
                    navController.navigate("appList") {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Два отдельных composable для AppList: с категорией и без
        composable("appList") {
            AppListScreen(
                category = null,
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetail.createRoute(appId))
                },
                onCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                },
                onClearCategory = {
                    // Уже на главной странице, ничего не делаем
                }
            )
        }

        composable("appList/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")

            AppListScreen(
                category = category,
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetail.createRoute(appId))
                },
                onCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                },
                onClearCategory = {
                    // Возвращаемся к списку всех приложений
                    navController.navigate("appList") {
                        // Очищаем стек до первого AppList
                        popUpTo("appList") {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.AppDetail.route,
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            AppDetailScreen(
                appId = appId,
                onBack = {
                    navController.popBackStack()
                },
                onScreenshotClick = { screenshotIndex ->
                    navController.navigate(Screen.Screenshots.createRoute(appId, screenshotIndex))
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                onBack = { navController.popBackStack() },
                onCategoryClick = { category ->
                    // Переходим к AppList с фильтром по категории
                    navController.navigate("appList/${category.name}")
                }
            )
        }

        composable(
            route = Screen.Screenshots.route,
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType },
                navArgument("startIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ScreenshotsScreen(
                appId = appId,
                startIndex = startIndex,
                onBack = { navController.popBackStack() }
            )
        }
    }
}