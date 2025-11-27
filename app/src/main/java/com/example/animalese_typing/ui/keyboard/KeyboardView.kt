package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ShiftState
import com.example.animalese_typing.ui.keyboard.layouts.Qwerty
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme


@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    currentLayout: KeyboardLayouts = KeyboardLayouts.QWERTY,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {},
    shiftState: ShiftState = ShiftState.OFF
) {
    val height by AnimalesePreferences(LocalContext.current).getKeyboardHeight().collectAsState(initial = 250f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AnimaleseColors.background)
            .padding(4.dp)
            .height(height.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .height(WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()-10.dp)
        )
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme {
        KeyboardView()
    }
}
