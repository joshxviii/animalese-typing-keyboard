package com.example.animalese_typing.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = KeyBase,
    onPrimary = KeyText,
    primaryContainer = KeyBase,
    onPrimaryContainer = KeyText,
    secondary = KeyBase,
    onSecondary = KeyText,
    secondaryContainer = KeyBase,
    onSecondaryContainer = KeyText,
    tertiary = KeyBase,
    onTertiary = KeyText,
    tertiaryContainer = KeyBase,
    onTertiaryContainer = KeyText,
    background = Background,
    onBackground = KeyText,
    surface = KeyTop,
    onSurface = KeyText,
    surfaceVariant = KeyTop,
    onSurfaceVariant = KeyText,
    outline = KeyText,
    inverseOnSurface = Background,
    inverseSurface = KeyText,
    inversePrimary = KeyTop,
    surfaceTint = KeyBase,
//    scrim = Color.Black, error: Unresolved reference: Black
)

@Composable
fun AnimaleseTypingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
