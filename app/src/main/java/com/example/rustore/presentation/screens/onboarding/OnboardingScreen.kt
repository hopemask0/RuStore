package com.example.rustore.presentation.screens.onboarding

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rustore.R
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(
    onContinue: () -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    // Для тестирования - раскомментируйте следующую строку чтобы сбросить онбординг
    sharedPreferences.edit().putBoolean("onboarding_shown", false).apply()

    // Проверяем, показывался ли онбординг
    val isFirstLaunch = remember {
        !sharedPreferences.getBoolean("onboarding_shown", false)
    }

    // Если не первый запуск, сразу переходим дальше
    LaunchedEffect(isFirstLaunch) {
        if (!isFirstLaunch) {
            onContinue()
        }
    }

    // Показываем онбординг только если это первый запуск
    if (isFirstLaunch) {
        OnboardingContent(
            onContinue = {
                // Сохраняем, что онбординг показан
                sharedPreferences.edit()
                    .putBoolean("onboarding_shown", true)
                    .apply()
                onContinue()
            }
        )
    }
}

@Composable
fun OnboardingContent(
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Логотип RuStore - улучшенная версия
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            // Приветственный текст
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Text(
                    text = stringResource(R.string.welcome_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            // Кнопка продолжения
            Button(
                onClick = onContinue,
                modifier = Modifier.width(200.dp),
                shape = androidx.compose.material3.MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    stringResource(R.string.get_started),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}