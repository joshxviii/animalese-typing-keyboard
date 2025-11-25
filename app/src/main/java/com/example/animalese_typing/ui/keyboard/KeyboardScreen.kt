package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyIcon


@Composable
fun KeyboardScreen(
    onKeyPress: (String) -> Unit = {},
    onChar: (Char) -> Unit = {},
    onBackspace: () -> Unit = {},
    onEnter: () -> Unit = {},
    onShiftToggle: () -> Unit = {},
    onModeChange: () -> Unit = {},
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {}
) {
    var isCaps by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = KeyIcon)
            }
            IconButton(onClick = onResize) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Resize Keyboard", tint = KeyIcon)
            }
        }

        // Keyboard
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            KeyboardLayouts.Qwerty.forEachIndexed { index, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    row.forEach { key ->
                        KeyButton(
                            key = key,
                            isCaps = isCaps,
                            onChar = onChar,
                            onBackspace = onBackspace,
                            onEnter = onEnter,
                            onShiftToggle = {
                                isCaps = !isCaps
                                onShiftToggle()
                            },
                            onModeChange = onModeChange,
                            modifier = Modifier.weight(key.weight)
                        )
                    }
                }
            }
        }
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 411, heightDp = 900)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme {
        KeyboardScreen()
    }
}
