package com.example.animalese_typing.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import kotlinx.coroutines.launch

@Composable
fun LayoutSettingsScreen(
    preferences: AnimalesePreferences = AnimalesePreferences(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val currentKeyboardHeight by preferences.getKeyboardHeight().collectAsState(initial = 0.3f)

    SettingsList {
        SettingsCategory("Keyboard Height")
        SliderItem(
            title = "Height",
            value = currentKeyboardHeight,
            onValueChange = { scope.launch { preferences.saveKeyboardHeight(it) } },
            valueRange = 0.4f..0.9f
        )

        SettingsCategory("Layout")
        RadioButtonItem(title = "QWERTY", selected = true, onClick = {})
        RadioButtonItem(title = "AZERTY", selected = false, onClick = {})
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LayoutSettingsScreenPreview() {
    AnimaleseTypingTheme {
        LayoutSettingsScreen()
    }
}