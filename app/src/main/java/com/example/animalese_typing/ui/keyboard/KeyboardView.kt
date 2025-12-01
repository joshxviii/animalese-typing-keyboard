package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimaleseIME
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ShiftState
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.keyboard.layouts.Layout
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme


/**
 * The full keyboard view.
 */
@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    currentLayout: KeyboardLayouts = KeyboardLayouts.QWERTY,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {},
    shiftState: ShiftState = ShiftState.OFF,
    pressedKey: Key? = null,
    imeService: AnimaleseIME? = null,
) {
    val height by AnimalesePreferences(LocalContext.current).getKeyboardHeight().collectAsState(initial = 250f)

    Box() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(AnimaleseColors.background)
                .padding(4.dp)
                .height(height.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Top Bar
            TopBar(
                onSettings = onSettings,
                onResize = onResize
            )

            // Keyboard
            KeyboardLayout(
                layout = currentLayout.layout,
                onKeyDown = onKeyDown,
                onKeyUp = onKeyUp,
                shiftState = shiftState,
                modifier = modifier
                    .weight(1f),
            )

            // Nav Bar Padding
            Box(
                modifier = modifier
                    .height(
                        WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() - 10.dp
                    )
            )
        }

        //TODO: key popups only overly on top of the keyboard
        // Need to extend bounds to cover the whole screen to overlay outside the bounds of the keyboard
//        if (pressedKey is Key.CharKey && pressedKey.showPopup) {
//            KeyPopout(
//                key = pressedKey,
//                isUppercase = shiftState != ShiftState.OFF,
//                modifier = Modifier
//                    .offset(y = (-42).dp)
//                    .align(pressedKey.coordinates as Alignment)
//                    .zIndex(1f)
//            )
//        }
    }

    LaunchedEffect(pressedKey) {
        if (pressedKey is Key.CharKey && pressedKey.showPopup && imeService != null) {
//            imeService.showKeyPopup(
//                x = pressedKey.coordinates?.positionOnScreen()?.x?.toInt() ?: 0,
//                y = pressedKey.coordinates?.positionOnScreen()?.y?.toInt() ?: 0
//            ) {
//                KeyPopout(
//                    key = pressedKey,
//                    isUppercase = shiftState != ShiftState.OFF,
//                    modifier = Modifier
//                )
//            }

            // Position the popup window
//            imeService.popupView?.let { popup ->
//                val params = popup.layoutParams as WindowManager.LayoutParams
//                params.x = location[0] + keyWidth / 2 - popupWidth / 2
//                params.y = location[1] - 220 // adjust this value to move popup up/down
//                params.width = popupWidth
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                imeService.windowManager.updateViewLayout(popup, params)
//            }
        } else {
            //imeService?.dismissKeyPopup()
        }
    }

}



@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    layout : Layout,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    shiftState: ShiftState = ShiftState.OFF,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AnimaleseColors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        layout.value.forEachIndexed { index, row ->
            key(index) {// use an index key so jetpack doesn't try to reused old Key info
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = AnimaleseColors.background)
                        .weight(1f),
                ) {
                    row.forEach { key ->
                        key(key) {
                            KeyButton(
                                key = key,
                                modifier = Modifier
                                    .weight(key.weight)
                                    .onGloballyPositioned { c ->
                                        key.size = c.size

                                        val position = c.positionOnScreen()

                                        key.position = IntOffset(
                                            x = position.x.toInt(),
                                            y = position.y.toInt()
                                        )
                                    },
                                onKeyDown = onKeyDown,
                                onKeyUp = onKeyUp,
                                shiftState = shiftState
                            )
                        }
                    }
                }
            }
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme {
        KeyboardView()
    }
}
// endregion
