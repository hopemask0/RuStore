package com.example.rustore.presentation.navigation


sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object AppList : Screen("appList?category={category}") {
        fun createRoute(category: String? = null): String {
            return if (category != null) {
                "appList?category=$category"
            } else {
                "appList"
            }
        }
    }
    object AppDetail : Screen("appDetail/{appId}") {
        fun createRoute(appId: String) = "appDetail/$appId"
    }
    object Categories : Screen("categories")
    object Screenshots : Screen("screenshots/{appId}/{startIndex}") {
        fun createRoute(appId: String, startIndex: Int) = "screenshots/$appId/$startIndex"
    }

    // Добавляем метод для создания route с категорией
    companion object {
        fun createAppListRouteWithCategory(category: String) = "appList?category=$category"
    }
}