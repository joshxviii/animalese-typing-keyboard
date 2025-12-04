package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
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
    val storedTheme by AnimalesePreferences(LocalContext.current).getTheme().collectAsState(initial = AnimaleseThemes.Light)
    val useSystemTheme by AnimalesePreferences(LocalContext.current).getSystemDefaultTheme().collectAsState(initial = true)
    val currentTheme = theme?:
        if (useSystemTheme) if (isSystemInDarkTheme()) AnimaleseThemes.Dark else AnimaleseThemes.Light
        else storedTheme

    CompositionLocalProvider(LocalThemeColors provides currentTheme) {
        MaterialTheme(
            colorScheme = androidx.compose.material3.darkColorScheme()
                .copy(
                    background = currentTheme.background,
                    onBackground = currentTheme.defaultText,
                    surface = currentTheme.keyBase,
                    onSurface = currentTheme.defaultText,
                    primary = currentTheme.keyBaseHighlight,
                    onPrimary = currentTheme.keyBase
                ),
            content = content
        )
    }
}

val LocalThemeColors = staticCompositionLocalOf { AnimaleseThemes.Light }

/**
 * Provides access to the current theme's colors.
 *
 * Usage: `Theme.colors.keyBase`
 */
object Theme {
    val colors: ThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeColors.current
}
