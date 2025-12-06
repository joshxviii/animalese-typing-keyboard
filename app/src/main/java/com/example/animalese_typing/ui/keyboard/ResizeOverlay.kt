package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.drag

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.AnimaleseTyping.Companion.logMessage
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.ui.theme.opacity
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ResizeOverlay(// Resize overlay
    onToggleResizeClick: () -> Unit = {},
    bottomPadding: Dp = 0.dp
) {

    Box(
        Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggleResizeClick
            )
    ) {
        ResizableBox(
            Modifier.align(Alignment.BottomCenter)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = onToggleResizeClick
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.ic_control),
                    contentDescription = "",
                    tint = Theme.colors.keyBaseHighlight
                )
            }
        }
    }
}

@Composable
fun ResizableBox(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit) = {}
) {
    val preferences = AnimalesePreferences(LocalContext.current)
    val currentKeyboardHeight by preferences.getKeyboardHeight().collectAsState(initial = 250f)
    var resizeHeight by remember(currentKeyboardHeight) { mutableStateOf(currentKeyboardHeight) }

    val scope = rememberCoroutineScope()
    fun onDragEnd() {
        scope.launch { preferences.saveKeyboardHeight(resizeHeight) }
    }

    // Main content box
    key(currentKeyboardHeight) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(resizeHeight.dp)
                .background(Theme.colors.background.opacity(0.4f))
                .border(2.dp, Theme.colors.keyBaseHighlight)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
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
            content()
        }

        ResizeHandle(
            onDrag = { delta ->
                val newHeight = (resizeHeight.dp + delta).value.coerceIn(AnimalesePreferences.HEIGHT_RANGE)
                resizeHeight = newHeight
            },
            onDragEnd = ::onDragEnd,
            modifier = modifier
        ) // Bottom
        ResizeHandle(
            onDrag = { delta ->
                val newHeight = (resizeHeight.dp - delta).value.coerceIn(AnimalesePreferences.HEIGHT_RANGE)
                resizeHeight = newHeight
            },
            onDragEnd = ::onDragEnd,
            modifier = modifier.offset(0.dp, (-resizeHeight+32).dp)
        ) // Top
    }
}

@Composable
private fun ResizeHandle(
    onDrag: (Dp) -> Unit,
    onDragEnd: () -> Unit = {},
    modifier: Modifier = Modifier
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
                    onDragEnd = onDragEnd
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