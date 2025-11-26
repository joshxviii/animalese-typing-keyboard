package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.animalese_typing.ui.keyboard.KeyboardLayouts

@Composable
fun AnimaleseTypingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkMode else LightMode

    CompositionLocalProvider(LocalAnimaleseColors provides colors) {
        MaterialTheme(
            colorScheme = androidx.compose.material3.darkColorScheme()
                .copy(
                    primary = colors.highlight,
                    surface = colors.keyBase,
                    background = colors.background,
                ),
            content = content
        )
    }
}