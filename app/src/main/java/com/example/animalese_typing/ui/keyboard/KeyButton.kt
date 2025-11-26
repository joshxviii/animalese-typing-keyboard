package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.darken

// Data model for keyboard keys
sealed class Key(
    val code: Int?,
    val weight: Float,
    val isRepeatable: Boolean = false,
    val type : String,
    val data : String?
) {
    class CharKey(
        val char: Char,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(char.code, weight, isRepeatable, type, data)

    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(null, weight, isRepeatable, type, data)

    class IconKey(
        val id: Int,
        code: Int = -1,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        val size: Dp = 32.dp,
        type : String = "",
        data : String? = null
    ) : Key(code, weight, isRepeatable, type, data)

    class TextKey(
        val text: String,
        code: Int? = null,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(code, weight, isRepeatable, type, data)
}

@Composable
fun KeyButton(
    key: Key,
    modifier: Modifier = Modifier,
    isCaps: Boolean = false,
    onChar: (Char) -> Unit = {},
    onBackspace: () -> Unit = {},
    onEnter: () -> Unit = {},
    onShiftToggle: () -> Unit = {},
    onModeChange: (String?) -> Unit = {}
) {
    val keyModifier = modifier.clip(RoundedCornerShape(33))

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val base = when (key.type) {
        "alt" -> AnimaleseColors.keyBaseAlt
        "highlight" -> AnimaleseColors.highlight
        else -> AnimaleseColors.keyBase
    }
    val label = when (key.type) {
        "alt" -> AnimaleseColors.keyTextAlt
        "highlight" -> Color.White
        else -> AnimaleseColors.keyText
    }

    val topColor = if (isPressed) base.darken() else base
    val bottomColor = if (isPressed) base.darken(.15f) else base.darken()
    val labelColor = if (isPressed) label.darken() else label

    val defaultTextStyle = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.arial_rounded_bold, FontWeight.Bold)
        ),
        fontSize = 22.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = labelColor.copy(alpha = 0.5f),
            offset = Offset(1.0f, 1.0f),
            blurRadius = 1.5f
        )
    )

    if (key !is Key.Empty) Box(
        modifier = keyModifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {

                    when (key.code) {
                        -1 -> onShiftToggle()
                        -5 -> onBackspace()
                        10 -> onEnter()
                        else -> {
                            if (key is Key.CharKey) {
                                onChar(
                                    if (isCaps) key.char.uppercaseChar()
                                    else key.char
                                )
                            }
                        }
                    }
                    if (key.data != null) onModeChange(key.data)
                }
            )
 // For Button Depth Effect
//            .background(bottomColor)
//            .padding(bottom = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = keyModifier
                .background(topColor)
                .fillMaxSize()
        ) {
            when (key) {
                is Key.CharKey -> {
                    Text(
                        text = if (isCaps) key.char.uppercase() else key.char.toString(),
                        color = labelColor,
                        textAlign = TextAlign.Center,
                        style = defaultTextStyle
                    )
                }

                is Key.IconKey -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(key.id),
                        contentDescription = "",
                        tint = labelColor,
                        modifier = Modifier.size(key.size)
                    )
                }

                is Key.TextKey -> {
                    Text(
                        text = key.text,
                        color = labelColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        style = defaultTextStyle
                    )
                }

                else -> {}
            }
        }
    } else {
        Box(modifier = modifier)
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 100, heightDp = 200)
@Composable
fun KeyButtonPreview() {
    AnimaleseTypingTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            KeyButton(modifier = Modifier.weight(1f), key = Key.CharKey('a'))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(R.drawable.ic_shift_lock))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(id=R.drawable.ic_enter, type = "highlight"))
        }
    }
}