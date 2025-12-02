package com.example.rustore.presentation.screens.appdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore.data.models.App
import com.example.rustore.presentation.components.CenterLoading
import com.example.rustore.presentation.components.ErrorState
import com.example.rustore.presentation.screens.appdetail.components.AppDetailContent
import com.example.rustore.presentation.theme.RuStoreTheme
import com.example.rustore.presentation.viewmodels.AppDetailViewModel

@Composable
fun AppDetailScreen(
    appId: String,
    onBack: () -> Unit,
    onScreenshotClick: (Int) -> Unit
) {
    val viewModel: AppDetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(appId) {
        viewModel.loadApp(appId)
    }

    when {
        uiState.isLoading -> CenterLoading()
        uiState.error != null -> ErrorState(
            message = uiState.error!!,
            onRetry = { viewModel.loadApp(appId) }
        )
        uiState.app != null -> {
            AppDetailContent(
                app = uiState.app!!,
                onBack = onBack,
                onScreenshotClick = onScreenshotClick,
                onInstallClick = {
                    // TODO: Реализовать установку приложения
                }
            )
        }
        else -> ErrorState(
            message = "Приложение не найдено",
            onRetry = { viewModel.loadApp(appId) }
        )
    }
}

@Preview
@Composable
fun AppDetailScreenPreview() {
    RuStoreTheme {
        val sampleApp = App(
            id = "1",
            name = "СберБанк Онлайн",
            developer = "Сбербанк",
            description = "Мобильное приложение СберБанка для управления счетами, картами и переводами. Позволяет оплачивать услуги, переводить деньги, открывать вклады и многое другое.",
            shortDescription = "Банковские услуги и платежи",
            category = com.example.rustore.data.models.AppCategory.FINANCE,
            ageRating = com.example.rustore.data.models.AgeRating.SIX_PLUS,
            iconRes = android.R.drawable.ic_menu_save,
            screenshots = listOf(android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_camera)
        )

        AppDetailContent(
            app = sampleApp,
            onBack = { },
            onScreenshotClick = { },
            onInstallClick = { }
        )
    }
}