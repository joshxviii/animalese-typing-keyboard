package com.example.animalese_typing.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

/**
 * Color values used for the keyboard theme.
 */
data class ThemeColors(
    val background: Color,
    val keyBase: Color,
    val keyBaseAlt: Color,
    val keyText: Color,
    val keyTextAlt: Color,
    val keyIcon: Color,
    val highlight: Color,
)
object AnimaleseThemes {
    val Light: ThemeColors = ThemeColors(
        background = Color(0xFFF1F0F7),
        keyBase = Color(0xFFFFFFFF),
        keyBaseAlt = Color(0xFFCFE1FF),
        keyText = Color(0xFF484F50),
        keyTextAlt = Color(0xFF1A1B20),
        keyIcon = Color(0xFF1A1B20),
        highlight = Color(0xFF2ED3CE)
    )

    val Dark: ThemeColors = ThemeColors(
        background = Color(0xFF1A1B20),
        keyBase = Color(0xFF2F3036),
        keyBaseAlt = Color(0xFF45464C),
        keyText = Color(0xFFF1F0F7),
        keyTextAlt = Color(0xFFF1F0F7),
        keyIcon = Color(0xFFF1F0F7),
        highlight = Color(0xFF00D0CB)
    )

    val Cream: ThemeColors = ThemeColors(
        background = Color(0xFFFFFAE4),
        keyBase = Color(0xFFECE0D0),
        keyBaseAlt = Color(0xFFDECBB2),
        keyText = Color(0xFFB49162),
        keyTextAlt = Color(0xFFB9935E),
        keyIcon = Color(0xFFD9BD96),
        highlight = Color(0xFF2ED3CE)
    )

    val Chocolate: ThemeColors = ThemeColors(
        background = Color(0xFF2B2218),
        keyBase = Color(0xFF3A3429),
        keyBaseAlt = Color(0xFF4A3F30),
        keyText = Color(0xFFE8D5B5),
        keyTextAlt = Color(0xFFF0D4A8),
        keyIcon = Color(0xFFF0D9B0),
        highlight = Color(0xFF00D0CB)
    )

    /**
     * Parses a theme name string into a [ThemeColors].
     *
     * @param name The case-insensitive name of the theme.
     */
    fun fromName(name: String): ThemeColors {
        return themes[name.uppercase()] ?: Light
    }

    val themes = mapOf(
        "LIGHT" to Light,
        "DARK" to Dark,
        "CREAM" to Cream,
        "CHOCOLATE" to Chocolate
    )
}

/**
 * Darkens light [Color] and lightens dark colors by blending towards black or white.
 *
 * @param amount The fraction to blend, between 0.0 and 1.0.
 */
fun Color.highlight(amount: Float = 0.125f): Color {
    val luminance = ColorUtils.calculateLuminance(this.toArgb())
    val isDark = luminance < 0.5f

    val targetColor = if (isDark) Color.White else Color.Black
    return lerp(this, targetColor, amount)
}

/**
 * Change the alpha value on a [Color] by an amount
 */
fun Color.opacity(amount: Float): Color {
    return copy(alpha = amount)
}