package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
private const val Y_OFFSET = 126

/**
 * A popup overlay used for alternative key menus and pressed key popups.
 *
 * @param key The key that is being pressed.
 * @param popupMenuActive Whether a key's popup menu should be shown.
 * @param pointerPosition The position of the pointer on the screen used for navigating popupmenu.
 */
@Composable
fun PopoutOverlay(
    key: Key?,
    popupMenuActive: Boolean = false,
    pointerPosition: Offset = Offset.Unspecified,
) {
    /**
     * Used to move overlay past the bounds of the keyboard.
     * pointerPosition will need to be adjusted based on this value as well.
     */
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt() }

    Popup(
        properties = PopupProperties(
            clippingEnabled = false,
            focusable = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            excludeFromSystemGesture = true,
        ),
        popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset {// Only show popup when key is pressed
                return if (key is Key.CharKey && popupMenuActive) IntOffset(0,-Y_OFFSET)
                //return if (key is Key.CharKey && key.showPopup) IntOffset(0,-Y_OFFSET)
                else IntOffset(0, 9999)
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.Red)
        ) {
            Layout(
                content = {
                    if (key is Key.CharKey) {
                        if (popupMenuActive) KeyPopoutMenu(
                            key = key,
                            pointerPosition = Offset(
                                pointerPosition.x,
                                pointerPosition.y + Y_OFFSET
                            )
                        )
                        else KeyPopout(key = key)
                    }
                }
            ) { measurables, constraints ->
                val placeables =
                    measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
                val keySize = key?.size ?: IntSize.Zero
                val keyPosition = key?.position ?: IntOffset.Zero
                val popupSize = IntSize(
                    placeables.maxOfOrNull { it.width } ?: 0,
                    placeables.maxOfOrNull { it.height } ?: 0
                )
                val popupPosition = IntOffset(
                    (keyPosition.x - popupSize.width / 2).coerceIn(0..screenWidth-popupSize.width),
                    keyPosition.y
                )

                layout(popupSize.width, popupSize.height) {
                    placeables.forEach {
                        it.placeRelative(popupPosition.x, popupPosition.y)
                    }
                }
            }
        }
    }
}