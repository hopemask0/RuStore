package com.example.rustore.presentation.screens.applist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore.presentation.components.RuStoreLogo
import com.example.rustore.presentation.screens.appdetail.components.AppCard
import com.example.rustore.presentation.theme.RuStoreTheme
import com.example.rustore.presentation.viewmodels.AppListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    category: String? = null,
    onAppClick: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onClearCategory: () -> Unit = {}  // ДОБАВЬТЕ ЭТОТ ПАРАМЕТР
) {
    val viewModel: AppListViewModel = viewModel()
    val apps by viewModel.apps.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val categoryDisplayName by viewModel.categoryDisplayName.collectAsState()

    // Загружаем приложения при изменении категории
    LaunchedEffect(category) {
        viewModel.loadApps(category)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RuStoreLogo(size = 32.dp)
                        Text(
                            text = if (categoryDisplayName != null) {
                                "RuStore - $categoryDisplayName"
                            } else {
                                "RuStore"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    if (category != null) {
                        IconButton(onClick = onClearCategory) {  // ИСПОЛЬЗУЕМ onClearCategory
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад ко всем приложениям"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Кнопка категорий
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Button(
                    onClick = {
                        if (category != null) {
                            // Если есть фильтр по категории, очищаем его
                            onClearCategory()
                        } else {
                            // Если фильтра нет, переходим к категориям
                            onCategoriesClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = "Категории",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (category != null) {
                            "Все приложения"
                        } else {
                            "Показать категории"
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Список приложений
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (apps.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (categoryDisplayName != null) {
                            "Нет приложений в категории \"$categoryDisplayName\""
                        } else {
                            "Нет приложений"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(apps) { app ->
                        AppCard(
                            app = app,
                            onAppClick = onAppClick
                        )
                    }
                }
            }
        }
    }
}