package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.OnColor

@Composable
fun KeyText(
    text: String,
    color: Color,
) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(
            Font(R.font.arial_rounded_bold, FontWeight.Bold)
        ),

        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = color.copy(alpha = 0.5f),
            offset = Offset(1.0f, 1.0f),
            blurRadius = 1.5f
        )
    )
    val defaultAutoSize = TextAutoSize.StepBased(
        minFontSize = 8.sp,
        maxFontSize = 60.sp,
        stepSize = 2.sp
    )

    BasicText(
        text = text,
        style = defaultTextStyle,
        autoSize = defaultAutoSize,
        softWrap = false,
        color = OnColor(color)
    )
}