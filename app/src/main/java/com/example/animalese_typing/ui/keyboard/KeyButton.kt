package com.example.animalese_typing.ui.keyboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.AnimaleseIME
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme
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
    onPointerMove: (Offset) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
) {
    val isPressed : MutableState<Boolean> = remember { mutableStateOf(false) }

    // get generic colors based on key type
    val (base, label) = when (key.type) {
        "alt" -> Theme.colors.keyBaseAlt to Theme.colors.keyTextAlt
        "highlight" -> Theme.colors.highlight to Color.White
        else -> Theme.colors.keyBase to Theme.colors.keyText
    }

    // get the final color for key's label/base
    val shouldShowPopup = key is Key.CharKey && key.showPopup
    val (baseColor, labelColor) = when {
        isPressed.value && shouldShowPopup -> Color.Transparent to label.opacity(0.1f)
        isPressed.value -> base.highlight() to label.highlight()
        else -> base to label
    }

    if (key !is Key.Empty)
    BoxWithConstraints(// Key interaction size
        modifier = modifier
            .fillMaxHeight()
            .pointerInput(key) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    onKeyDown(key)
                    isPressed.value = true

                    drag(down.id) { change: PointerInputChange ->
                        onPointerMove(change.position - Offset(key.size.width.toFloat(), -(key.size.height.toFloat()/2)))// pointer pos relative to the key
                        change.consume()
                    }

                    onKeyUp(key)
                    isPressed.value = false
                }
            }
//            .background(Color.Red) // for debugging
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box( // key base color
                modifier = modifier
                    .padding(2.dp, 0.dp, 2.dp, 10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(baseColor)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (key is Key.CharKey && key.subChars.isNotEmpty() && key.altKeyHint) KeyText( // tiny sub char label
                    modifier = Modifier
                        .padding(3.dp, 2.dp)
                        .height(14.dp)
                        .align(Alignment.TopEnd),
                    text = "${key.subChars[0]}",
                    color = labelColor.opacity(0.3f),
                )
                Box(
                    // Text/icon size limiter
                    modifier = modifier
                        //.fillMaxSize(0.70f)
                        .padding(4.dp),
                ) {
                    when (key) {
                        is Key.CharKey -> {
                            key.isUpperCase = shiftState != AnimaleseIME.ShiftState.OFF
                            KeyText(
                                modifier = Modifier.align(Alignment.Center),
                                text = "${if (key.isUpperCase) key.char.uppercase() else key.char}",
                                color = labelColor,
                                size = 20.sp
                            )
                        }
                        is Key.IconKey -> {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(
                                    if (key.event == KeyFunctions.SHIFT) when (shiftState) {
                                        AnimaleseIME.ShiftState.OFF -> R.drawable.ic_shift_off
                                        AnimaleseIME.ShiftState.ON -> R.drawable.ic_shift_on
                                        AnimaleseIME.ShiftState.LOCKED -> R.drawable.ic_shift_lock
                                    }
                                    else key.iconId
                                ),
                                contentDescription = "",
                                tint = labelColor,
                            )
                        }
                        is Key.TextKey -> {
                            KeyText(
                                modifier = Modifier.align(Alignment.Center),
                                text = key.text,
                                color = labelColor,
                                size = 16.sp
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier)
    }
}

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 40, heightDp = 210)
@Composable
fun KeyButtonPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        Column(modifier = Modifier.background(color=Color(0xFF1E1F22))) {
            KeyButton(modifier = Modifier.weight(1f), key = Key.CharKey('W', subChars = listOf('1', '2', '3')))
            KeyButton(modifier = Modifier.weight(1f), key = Key.CharKey('a'))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(R.drawable.ic_shift_lock))
            KeyButton(modifier = Modifier.weight(1f), key = Key.IconKey(iconId=R.drawable.ic_enter, type = "highlight"))
        }
    }
}
// endregion