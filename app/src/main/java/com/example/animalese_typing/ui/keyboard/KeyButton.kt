package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyBase
import com.example.animalese_typing.ui.theme.KeyIcon
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.KeyTop

// Data model for keyboard keys
sealed class Key(
    val code: Int,
    val weight: Float,
    val isRepeatable: Boolean = false
) {
    class TextKey(
        val text: String,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false
    ) : Key(text.first().code or -1, weight, isRepeatable)

    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false
    ) : Key(-1, weight, isRepeatable)

    class IconKey(
        val imageVector: ImageVector,
        val contentDescription: String,
        code: Int,
        weight: Float,
        isRepeatable: Boolean = false
    ) : Key(code, weight, isRepeatable)

    class SpecialTextKey(
        val text: String,
        code: Int,
        weight: Float,
        isRepeatable: Boolean = false
    ) : Key(code, weight, isRepeatable)
}

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    key: Key,
    isCaps: Boolean = false,
    onChar: (Char) -> Unit = {},
    onBackspace: () -> Unit = {},
    onEnter: () -> Unit = {},
    onShiftToggle: () -> Unit = {},
    onModeChange: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val topColor = if (isPressed) KeyBase else KeyTop
    val bottomColor = if (isPressed) KeyTop else KeyBase
    val textColor = KeyText

    if (key !is Key.Empty) Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    when (key.code) {
                        -1 -> onShiftToggle()
                        -5 -> onBackspace()
                        10 -> onEnter()
                        -2 -> onModeChange()
                        else -> {
                            val char = when (key) {
                                is Key.TextKey -> if (isCaps) key.text.uppercase()[0] else key.text[0]
                                else -> key.code.toChar()
                            }
                            onChar(char)
                        }
                    }
                }
            )
//            .background(bottomColor)
//            .padding(bottom = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(topColor),
            contentAlignment = Alignment.Center
        ) {
            when (key) {
                is Key.TextKey -> {
                    Text(
                        text = if (isCaps) key.text.uppercase() else key.text,
                        color = textColor,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = textColor.copy(alpha = 0.5f),
                                offset = Offset(1.0f, 1.0f),
                                blurRadius = 1.5f
                            )
                        )
                    )
                }

                is Key.IconKey -> {
                    Icon(
                        imageVector = key.imageVector,
                        contentDescription = key.contentDescription,
                        tint = KeyIcon,
                        modifier = Modifier.size(24.dp)
                    )
                }

                is Key.SpecialTextKey -> {
                    Text(
                        text = key.text,
                        color = textColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = textColor.copy(alpha = 0.5f),
                                offset = Offset(1.0f, 1.0f),
                                blurRadius = 1.5f
                            )
                        )
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
@Preview(showBackground = true, widthDp = 411, heightDp = 300)
@Composable
fun KeyButtonPreview() {
    AnimaleseTypingTheme {
        Box() {
            KeyButton(key = Key.TextKey("a"))
        }
    }
}