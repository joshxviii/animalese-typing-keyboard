package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.animalese_typing.AnimalesePreferences

/**
 * Applies the Animalese Theme to all it's children.
 */
@Composable
fun AnimaleseTypingTheme(
    theme: ThemeColors? = null,
    content: @Composable () -> Unit
) {
    val storedTheme by AnimalesePreferences(LocalContext.current).getTheme().collectAsState(initial = Light)
    val useSystemTheme by AnimalesePreferences(LocalContext.current).getSystemDefaultTheme().collectAsState(initial = true)
    val currentTheme = theme?:
        if (useSystemTheme) if (isSystemInDarkTheme()) Dark else Light
        else storedTheme

    CompositionLocalProvider(LocalAnimaleseColors provides currentTheme) {
        MaterialTheme(
            colorScheme = androidx.compose.material3.darkColorScheme()
                .copy(
                    background = currentTheme.background,
                    onBackground = currentTheme.keyText,
                    surface = currentTheme.keyBase,
                    onSurface = currentTheme.keyText,
                    primary = currentTheme.highlight,
                    onPrimary = currentTheme.keyBase
                ),
            content = content
        )
    }
}