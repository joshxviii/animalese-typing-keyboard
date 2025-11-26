package com.example.animalese_typing.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun LayoutSettingsScreen() {
    SettingsList {
        SettingsCategory("Keyboard Height")
        SliderItem(
            title = "Height",
            value = 0.6f,
            onValueChange = {},
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