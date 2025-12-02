package com.example.rustore.presentation.screens.screenshots

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore.presentation.components.CenterLoading
import com.example.rustore.presentation.components.ErrorState
import com.example.rustore.presentation.viewmodels.ScreenshotsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScreenshotsScreen(
    appId: String,
    startIndex: Int,
    onBack: () -> Unit
) {
    val viewModel: ScreenshotsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Инициализация пейджера
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { uiState.screenshots.size }
    )

    LaunchedEffect(appId) {
        viewModel.loadApp(appId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (uiState.screenshots.isNotEmpty()) {
                        Text("${pagerState.currentPage + 1} / ${uiState.screenshots.size}")
                    } else {
                        Text("Скриншоты")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> CenterLoading()
            uiState.error != null -> ErrorState(
                message = uiState.error!!,
                onRetry = { viewModel.loadApp(appId) }
            )
            uiState.screenshots.isNotEmpty() -> {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = uiState.screenshots[page]),
                            contentDescription = "Скриншот ${page + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            else -> ErrorState(
                message = "Скриншоты не найдены",
                onRetry = { viewModel.loadApp(appId) }
            )
        }
    }
}