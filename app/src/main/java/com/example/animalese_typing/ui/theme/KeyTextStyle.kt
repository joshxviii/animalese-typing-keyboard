package com.example.animalese_typing.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.OnColor

@Composable
fun KeyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.keyLabel,
    size: TextUnit? = null
) {
    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(
            Font(R.font.arial_rounded_bold, FontWeight.Bold)
        ),

        letterSpacing = 0.0.sp,
    )
    val defaultAutoSize = TextAutoSize.StepBased(
        minFontSize = size ?: 8.sp,
        maxFontSize = size ?: 60.sp,
        stepSize = 2.sp
    )

    BasicText(
        modifier = modifier,
        text = text,
        style = defaultTextStyle,
        autoSize = defaultAutoSize,
        softWrap = false,
        color = OnColor(color)
    )
}

@Preview(showBackground = true)
@Composable
fun KeyTextStylePreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        Column() {
            KeyText(
                text = "Text",
                size = 30.sp
            )
        }
    }
}