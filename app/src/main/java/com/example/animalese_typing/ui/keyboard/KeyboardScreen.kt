package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.keyboard.layouts.Qwerty
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme


@Composable
fun KeyboardScreen(
    height: Dp = 250.dp,
    onKeyPress: () -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {},
    onChar: (Char) -> Unit = {},
    onBackspace: () -> Unit = {},
    onEnter: () -> Unit = {},
    onShiftToggle: () -> Unit = {},
    onSendData: (String?) -> Unit = {},
) {
    var isCaps by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AnimaleseColors.background)
            .padding(4.dp)
            .height(height),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        TopBar(
            onSettings = onSettings,
            onResize = onResize
        )

        // Keyboard
        KeyboardLayout(layout = Qwerty)
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 411, heightDp = 250)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme {
        KeyboardScreen()
    }
}
