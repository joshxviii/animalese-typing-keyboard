package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.utils.opacity

/**
 * Overlay used for resizing the keyboard.
 *
 * @param initialHeight The initial height of the keyboard.
 */
@Composable
fun ResizeOverlay(
    initialHeight: Float = 250f,
    onResizeClick: (Boolean) -> Unit = {},
    onDragEnd: (Float) -> Unit = {},
) {
    var resizeHeight by remember { mutableFloatStateOf(initialHeight) }

    Box(
        Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onResizeClick(false) }
            )
    ) {
        Box (
            Modifier
                .fillMaxWidth()
                .height(resizeHeight.dp)
                .background(Theme.colors.background.opacity(0.4f))
                .border(2.dp, Theme.colors.keyBaseHighlight)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
                .align(Alignment.BottomCenter)
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(Theme.colors.keyBaseHighlight)
                    .width(48.dp)
                    .height(20.dp)
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Theme.colors.keyBaseHighlight)
                    .width(48.dp)
                    .height(20.dp)
                    .align(Alignment.BottomCenter)
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = {onResizeClick(false)}
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.ic_control),
                    contentDescription = "",
                    tint = Theme.colors.keyBaseHighlight
                )
            }

            ResizeHandle(
                onDrag = { delta ->
                    val newHeight = (resizeHeight.dp - delta).value.coerceIn(AnimalesePreferences.HEIGHT_RANGE)
                    resizeHeight = newHeight
                },
                onDragEnd = { onDragEnd(resizeHeight) },
                modifier = Modifier
            ) // Bottom
            ResizeHandle(
                onDrag = { delta ->
                    val newHeight = (resizeHeight.dp + delta).value.coerceIn(AnimalesePreferences.HEIGHT_RANGE)
                    resizeHeight = newHeight
                },
                onDragEnd = { onDragEnd(resizeHeight) },
                modifier = Modifier.offset(0.dp, (-resizeHeight+32).dp)
            ) // Top
        }
    }
}

@Composable
private fun ResizeHandle(
    onDrag: (Dp) -> Unit,
    onDragEnd: (Float) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDrag(with(density) { dragAmount.y.toDp() })
                    },
                    onDragEnd = {onDragEnd(0f)}
                )
            }
            //.background(Color.White.copy(alpha = 0.8f))
    )
}

@Preview(showBackground = false)
@Composable
fun ResizeOverlayPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        ResizeOverlay()
    }
}