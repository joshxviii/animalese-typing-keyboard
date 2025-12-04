package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.IntOffset

@Composable
fun PopoutOverlay(
    modifier: Modifier = Modifier,
    key: Key?,
    showMenu: Boolean = false,
    pointerPosition: Offset = Offset.Unspecified,
    onSelectedIndexChange: (Int) -> Unit = {},
) {
    var overlayScreenOffset by remember { mutableStateOf(IntOffset.Zero) }

    Box(
        modifier = Modifier.onGloballyPositioned {
            val position = it.positionOnScreen()
            overlayScreenOffset = IntOffset(position.x.toInt(), position.y.toInt())
        }
//            .background(Color.Red.opacity(0.1f))
    ) {
        Layout(
            modifier = modifier,
            content = {
                if (showMenu) KeyPopoutMenu(
                    key = key,
                    pointerPosition = pointerPosition,
                    onSelectedIndexChange = onSelectedIndexChange
                )
                else KeyPopout(
                    key = key,
                )
            }
            // move the position of the popups to be above the key.
        ) { measurables, constraints ->
            if(key==null) { return@Layout layout(0,0) {} }
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                val keyScreenPosition = key.position
                val localKeyPosition = keyScreenPosition - overlayScreenOffset

                placeables.forEach { placeable ->
                    val idealX = localKeyPosition.x - placeable.width / 2
                    val constrainedX = idealX.coerceIn(
                        minimumValue = 0,
                        maximumValue = constraints.maxWidth - placeable.width
                    )
                    val yPosition = localKeyPosition.y - placeable.height

                    placeable.place(constrainedX, yPosition)
                }
            }
        }
    }
}