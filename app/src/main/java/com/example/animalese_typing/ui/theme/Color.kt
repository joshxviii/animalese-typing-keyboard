package com.example.animalese_typing.ui.theme

import androidx.compose.ui.graphics.Color

val Background = Color(0xFFFFFAE4)
val KeyBase = Color(0xFFECE0D0)
val KeyText = Color(0xFFCAAC84)
val KeyIcon = Color(0xFFD9BD96)
val Highlight = Color(0xFF00D0CB)

fun Color.darken(darkenBy: Float = 0.1f): Color {
    return copy(
        red = red * (1f - darkenBy),
        green = green * (1f - darkenBy),
        blue = blue * (1f - darkenBy),
        alpha = alpha
    )
}