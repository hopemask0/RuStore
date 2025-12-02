package com.example.rustore.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF0066CC),
    secondary = androidx.compose.ui.graphics.Color(0xFF66BB6A),
    background = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
)

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF3399FF),
    secondary = androidx.compose.ui.graphics.Color(0xFF81C784),
    background = androidx.compose.ui.graphics.Color(0xFF121212)
)

@Composable
fun RuStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}