package com.example.animalese_typing.ui.keyboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.animalese_typing.R
import com.example.animalese_typing.ShiftState
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.highlight
import com.example.animalese_typing.ui.theme.opacity

class OnColor(val color: Color) : ColorProducer {
    override fun invoke(): Color = color
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun KeyButton(
    key: Key,
    modifier: Modifier,
    onKeyUp: (Key) -> Unit = {},
    onKeyDown: (Key) -> Unit = {},
    shiftState: ShiftState = ShiftState.OFF,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (base, label) = when (key.type) {
        "alt" -> AnimaleseColors.keyBaseAlt to AnimaleseColors.keyTextAlt
        "highlight" -> AnimaleseColors.highlight to Color.White
        else -> AnimaleseColors.keyBase to AnimaleseColors.keyText
    }

    val baseColor   = if (isPressed) if (key is Key.CharKey) Color.Transparent else base.highlight() else base
    val labelColor  = if (isPressed) if (key is Key.CharKey) label.opacity(0.1f) else label.highlight() else label

    if (key !is Key.Empty)
    BoxWithConstraints(// Key interaction size
        modifier = modifier
            .fillMaxHeight()
            .pointerInput(key) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {}
                )
        ) {
            Box( // key base color
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .padding(2.dp, 0.dp, 2.dp, 10.dp)
                    .clip(RoundedCornerShape(33))
                    .background(baseColor)
                    .fillMaxSize()
            ) {
                Box( // Text/icon size limiter
                    modifier = modifier
                        .fillMaxSize(0.64f),
                    contentAlignment = Alignment.Center,
                ) {
                    when (key) {
                        is Key.CharKey -> {
                            val isUppercase = shiftState != ShiftState.OFF
                            KeyText(
                                text = if (isUppercase) key.char.uppercase() else key.char.toString(),
                                color = labelColor
                            )
                        }

                        is Key.IconKey -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (key.event == KeyFunctions.SHIFT) when (shiftState) {
                                        ShiftState.OFF -> R.drawable.ic_shift_off
                                        ShiftState.ON -> R.drawable.ic_shift_on
                                        ShiftState.LOCKED -> R.drawable.ic_shift_lock
                                    }
                                    else key.iconId
                                ),
                                contentDescription = "",
                                tint = labelColor,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        is Key.TextKey -> {
                            KeyText(
                                text = key.text,
                                color = labelColor
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
        if (isPressed && key is Key.CharKey && key.showPopup) {
            KeyPopout(
                key = key,
                isUppercase = shiftState != ShiftState.OFF,
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            )
        }
    } else {
        Box(modifier = modifier)
    }
}

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 70, heightDp = 160)
@Composable
fun KeyButtonPreview() {
    AnimaleseTypingTheme {
        Column(modifier = Modifier.background(color=Color(0xFF1E1F22))) {
            KeyButton(modifier = Modifier.weight(1f), key = Key.CharKey('a'))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(R.drawable.ic_shift_lock))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(iconId=R.drawable.ic_enter, type = "highlight"))
        }
    }
}
// endregion