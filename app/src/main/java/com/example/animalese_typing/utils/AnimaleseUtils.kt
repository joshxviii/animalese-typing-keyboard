package com.example.animalese_typing.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.IntOffset
import androidx.core.graphics.ColorUtils

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

fun IntOffset.toOffset(): Offset {
    return Offset(
        x = this.x.toFloat(),
        y = this.y.toFloat()
    )
}

fun Offset.toIntOffset(): IntOffset {
    return IntOffset(
        x = this.x.toInt(),
        y = this.y.toInt()
    )
}