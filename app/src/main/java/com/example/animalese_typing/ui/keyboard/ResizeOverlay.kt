package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.AnimaleseTyping.Companion.logMessage
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.ui.theme.opacity

@Composable
fun ResizeOverlay(// Resize overlay
    onToggleResizeClick: () -> Unit = {},
    bottomPadding: Dp = 0.dp
) {
    val height by AnimalesePreferences(LocalContext.current).getKeyboardHeight().collectAsState(initial = 250f)

    Box(
        modifier = Modifier
            .padding(bottom=bottomPadding)
            .fillMaxWidth()
            .height((height).dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top=0.dp, bottom=0.dp)
                .fillMaxSize()
                .background(Theme.colors.background.opacity(0.4f))
                .border(2.dp, Theme.colors.keyBaseHighlight)
                .clickable(// consume the interaction events
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                ),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(Theme.colors.keyBaseHighlight)
                    .width(48.dp)
                    .height(20.dp)
                    .align(Alignment.TopCenter)
                    .pointerInput(null) {
//                        awaitEachGesture {
//                            val down = awaitFirstDown()
//                            logMessage("Drag Start")
//                            drag(down.id) { change: PointerInputChange ->
//                                change.consume()
//                            }
//                            logMessage("Release")
//                        }
                    }
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Theme.colors.keyBaseHighlight)
                    .width(48.dp)
                    .height(20.dp)
                    .align(Alignment.BottomCenter)
                    .pointerInput(null) {
                    }
            )
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

@Preview(showBackground = false, heightDp = 250)
@Composable
fun ResizeOverlayPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        ResizeOverlay()
    }
}