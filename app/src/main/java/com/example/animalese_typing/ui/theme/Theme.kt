package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AnimaleseTypingTheme(
    theme: ThemeColors = if(isSystemInDarkTheme()) Dark else Light,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(LocalAnimaleseColors provides theme) {
        MaterialTheme(
            colorScheme = androidx.compose.material3.darkColorScheme()
                .copy(
                    background = theme.background,
                ),
            content = content
        )
    }
}