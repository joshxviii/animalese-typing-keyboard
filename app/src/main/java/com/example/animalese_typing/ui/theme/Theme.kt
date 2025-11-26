package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.animalese_typing.AnimalesePreferences

@Composable
fun AnimaleseTypingTheme(
    theme: ThemeColors? = null,
    content: @Composable () -> Unit
) {
    val storedTheme by AnimalesePreferences(LocalContext.current).getTheme().collectAsState(initial = Light)
    val currentTheme = theme?:storedTheme

    CompositionLocalProvider(LocalAnimaleseColors provides currentTheme) {
        MaterialTheme(
            colorScheme = androidx.compose.material3.darkColorScheme()
                .copy(
                    background = currentTheme.background,
                    surface = currentTheme.keyBase,
                    onSurface = currentTheme.keyText,

                    primary = currentTheme.highlight,
                    onPrimary = currentTheme.keyBase
                ),
            content = content
        )
    }
}