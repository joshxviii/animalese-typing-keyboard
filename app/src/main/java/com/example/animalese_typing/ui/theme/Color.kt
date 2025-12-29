package com.example.animalese_typing.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Color values used for the keyboard theme.
 */
data class ThemeColors(
    val background: Color,
    val defaultText: Color,
    val keyBase: Color,
    val keyBaseAlt: Color,
    val keyBaseHighlight: Color,
    val keyLabel: Color,
    val keyLabelAlt: Color,
    val keyLabelHighlight: Color,
    val iconButton: Color,

    // static colors
    val debugBackground: Color = Color(0xFFFF0000)
)
object AnimaleseThemes {
    val Light: ThemeColors = ThemeColors(
        background = Color(0xFFF1F0F7),
        defaultText = Color(0xFF27282F),
        keyBase = Color(0xFFFFFFFF),
        keyBaseAlt = Color(0xFFCFE1FF),
        keyBaseHighlight = Color(0xFF2ED3CE),
        keyLabel = Color(0xFF566062),
        keyLabelAlt = Color(0xFF27282F),
        keyLabelHighlight = Color(0xFFFFFFFF),
        iconButton = Color(0xFF566062),
    )
    val Dark: ThemeColors = ThemeColors(
        background = Color(0xFF1A1B20),
        defaultText = Color(0xFFF1F0F7),
        keyBase = Color(0xFF2F3036),
        keyBaseAlt = Color(0xFF45464C),
        keyBaseHighlight = Color(0xFF00D0CB),
        keyLabel = Color(0xFFD2D1D9),
        keyLabelAlt = Color(0xFFF1F0F7),
        keyLabelHighlight = Color(0xFFFFFFFF),
        iconButton = Color(0xFFD2D1D9),
    )
    val Cream: ThemeColors = ThemeColors(
        defaultText = Color(0xFFB9935E),
        background = Color(0xFFFFFAE4),
        keyBase = Color(0xFFECE0D0),
        keyBaseAlt = Color(0xFFDECBB2),
        keyBaseHighlight = Color(0xFF2ED3CE),
        keyLabel = Color(0xFFB49162),
        keyLabelAlt = Color(0xFFB9935E),
        keyLabelHighlight = Color(0xFFFFFFFF),
        iconButton = Color(0xFFD9BD96),
    )
    val Chocolate: ThemeColors = ThemeColors(
        background = Color(0xFF2B2218),
        defaultText = Color(0xFFF0D4A8),
        keyBase = Color(0xFF3A3429),
        keyBaseAlt = Color(0xFF4A3F30),
        keyBaseHighlight = Color(0xFF00D0CB),
        keyLabel = Color(0xFFE8D5B5),
        keyLabelAlt = Color(0xFFF0D4A8),
        keyLabelHighlight = Color(0xFFFFFFFF),
        iconButton = Color(0xFFF0D9B0),
    )

    val Cherry: ThemeColors = ThemeColors(
        background = Color(0xFFFFDFF2),
        defaultText = Color(0xFFF888C1),
        keyBase = Color(0xFFFDF2F8),
        keyBaseAlt = Color(0xFFFFF5FA),
        keyBaseHighlight = Color(0xFF2ED3CE),
        keyLabel = Color(0xFFFDB4D4),
        keyLabelAlt = Color(0xFFF888C1),
        keyLabelHighlight = Color(0xFFFFFFFF),
        iconButton = Color(0xFFFDB4D4),
    )

    /**
     * Parses a theme name string into a [ThemeColors].
     *
     * @param name The case-insensitive name of the theme.
     */
    fun fromName(name: String): ThemeColors {
        return themes[name.lowercase()] ?: Light
    }

    val themes = mapOf(
        "light" to Light,
        "dark" to Dark,
        "cream" to Cream,
        "chocolate" to Chocolate,
        "cherry" to Cherry,
    )
}