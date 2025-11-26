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
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.ThemeColors


@Composable
fun KeyboardScreen(
    height: Dp = 250.dp,
    onKeyPress: (String) -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {},
    onChar: (Char) -> Unit = {},
    onBackspace: () -> Unit = {},
    onEnter: () -> Unit = {},
    onShiftToggle: () -> Unit = {},
    onModeChange: (String?) -> Unit = {},
) {
    var isCaps by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp)
            .height(height),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Top Bar
            TopBar(
                onSettings = onSettings,
                onResize = onResize
            )

            // Keyboard
            KeyboardLayouts.Qwerty.forEachIndexed { index, row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = AnimaleseColors.background)
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    row.forEach { key ->
                        KeyButton(
                            key = key,
                            modifier = Modifier.weight(key.weight),
                            isCaps = isCaps,
                            onChar = onChar,
                            onBackspace = onBackspace,
                            onEnter = onEnter,
                            onShiftToggle = {
                                isCaps = !isCaps
                                onShiftToggle()
                            },
                            onModeChange = onModeChange
                        )
                    }
                }
            }
        }
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme {
        KeyboardScreen()
    }
}
