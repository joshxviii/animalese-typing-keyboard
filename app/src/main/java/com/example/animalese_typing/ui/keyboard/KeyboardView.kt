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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimaleseIME
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.keyboard.layouts.Layout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme


/**
 * The full keyboard view.
 */
@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    currentLayout: KeyboardLayouts = KeyboardLayouts.QWERTY,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onPointerMove: (Offset) -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
    showSuggestions: Boolean = false
) {
    val height by AnimalesePreferences(LocalContext.current).getKeyboardHeight().collectAsState(initial = 250f)

    Box() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.colors.background)
                .padding(4.dp)
                .height(height.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Top Bar
            TopBar(
                onSettings = onSettings,
                onResize = onResize,
                onSuggestionClick = onSuggestionClick,
                showSuggestions = showSuggestions
            )

            // Keyboard
            KeyboardLayout(
                layout = currentLayout.layout,
                onKeyDown = onKeyDown,
                onKeyUp = onKeyUp,
                onPointerMove = onPointerMove,
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
    }
}



@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    layout : Layout,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onPointerMove: (Offset) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        layout.value.forEachIndexed { index, row ->
            key(index) {// use an index key so jetpack doesn't try to reused old Key info
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = Theme.colors.background)
                        .weight(1f),
                ) {
                    row.forEach { key ->
                        key(key) {
                            KeyButton(
                                key = key,
                                modifier = Modifier
                                    .weight(key.weight),
                                onKeyDown = onKeyDown,
                                onKeyUp = onKeyUp,
                                onPointerMove = onPointerMove,
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
