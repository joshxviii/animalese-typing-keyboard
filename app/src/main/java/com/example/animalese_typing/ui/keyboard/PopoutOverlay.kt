package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.IntOffset
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun PopoutOverlay(
    modifier: Modifier = Modifier,
    key: Key?,
    showMenu: Boolean = false,
    menuSelectedIndex: Int = 0,
    pointerPosition: Offset,
    onSelectedIndexChange: (Int) -> Unit,
) {
    var overlayScreenOffset by remember { mutableStateOf(IntOffset.Zero) }
    var popupOffset by remember { mutableStateOf(IntOffset.Zero) }
    val itemPositions = remember { mutableStateMapOf<Int, Offset>() }

    LaunchedEffect(pointerPosition) {
        // if finger is lifted or menu isn't ready, select the first item by default
        if (pointerPosition == Offset.Unspecified || itemPositions.isEmpty()) {
            onSelectedIndexChange(0)
            return@LaunchedEffect
        }

        var closestIndex = 0
        var minDistance = Float.MAX_VALUE

        itemPositions.forEach { (index, itemCenter) ->
            val distance = sqrt(
                (pointerPosition.x - itemCenter.x).pow(2) + (pointerPosition.y - itemCenter.y).pow(2)
            )
            if (distance < minDistance) {
                minDistance = distance
                closestIndex = index
            }
        }
        onSelectedIndexChange(closestIndex)
    }
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
                    selectedIndex = menuSelectedIndex,
                    itemPositions = itemPositions,
                    touchOffset = popupOffset
                )
                else KeyPopout(
                    key = key,
                )
            }
            // move the position of the popups to be above the key. save popupOffset for drag inputs
        ) { measurables, constraints ->
            itemPositions.clear()
            if(key==null) { return@Layout layout(0,0) {} }
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                val keyScreenPosition = key.position
                val localKeyPosition = keyScreenPosition - overlayScreenOffset
                placeables.forEach { placeable ->
                    popupOffset = IntOffset(placeable.width/2, (placeable.height) )
                    placeable.place(localKeyPosition - popupOffset)
                }
            }
        }
    }
}