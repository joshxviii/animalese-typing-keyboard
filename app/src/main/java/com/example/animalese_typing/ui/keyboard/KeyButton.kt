package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.darken

class OnColor(val color: Color) : ColorProducer {
    override fun invoke(): Color = color
}

@Composable
fun KeyButton(
    key: Key,
    modifier: Modifier,
    onKeyUp: (Key) -> Unit = {},
    onKeyDown: (Key) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (base, label) = when (key.type) {
        "alt" -> AnimaleseColors.keyBaseAlt to AnimaleseColors.keyTextAlt
        "highlight" -> AnimaleseColors.highlight to Color.White
        else -> AnimaleseColors.keyBase to AnimaleseColors.keyText
    }

    val darkenAmount = if (isPressed) 0.15f else 0f

    val topColor    = base.darken(darkenAmount)
    val labelColor  = label.darken(darkenAmount)

    val defaultTextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(
            Font(R.font.arial_rounded_bold, FontWeight.Bold)
        ),

        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = labelColor.copy(alpha = 0.5f),
            offset = Offset(1.0f, 1.0f),
            blurRadius = 1.5f
        )
    )
    val defaultAutoSize = TextAutoSize.StepBased(
        minFontSize = 8.sp,
        maxFontSize = 60.sp,
        stepSize = 2.sp
    )

    if (key !is Key.Empty)
    Box(// Key interaction size
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitFirstDown(requireUnconsumed = false)
                        onKeyDown(key)
                        waitForUpOrCancellation()
                        onKeyUp(key)
                    }
                }
            }
//            .background(Color.Red) // for debugging
    ) {
        Box( // key base color
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(2.dp,0.dp,2.dp,10.dp)
                .clip(RoundedCornerShape(33))
                .background(topColor)
                .fillMaxSize()
        ) {
            Box( // Text/icon size limiter
                modifier = modifier
                    .fillMaxSize(0.64f),
                contentAlignment = Alignment.Center,
            ) {
                when (key) {
                    is Key.CharKey -> {
                        BasicText(
                            text = if (false) key.char.uppercase() else key.char.toString(),
                            style = defaultTextStyle,
                            autoSize = defaultAutoSize,
                            softWrap = false,
                            color = OnColor(labelColor)
                        )
                    }

                    is Key.IconKey -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(key.id),
                            contentDescription = "",
                            tint = labelColor,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is Key.TextKey -> {
                        BasicText(
                            text = key.text,
                            style = defaultTextStyle,
                            autoSize = defaultAutoSize,
                            softWrap = false,
                            color = OnColor(labelColor)
                        )
                    }

                    else -> {}
                }
            }
        }
    } else {
        Box(modifier = modifier)
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 70, heightDp = 160)
@Composable
fun KeyButtonPreview() {
    AnimaleseTypingTheme {
        Column(modifier = Modifier.background(color=Color(0xFF1E1F22))) {
            KeyButton(modifier = Modifier.weight(1f), key = Key.CharKey('a'))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(R.drawable.ic_shift_lock))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(id=R.drawable.ic_enter, type = "highlight"))
        }
    }
}