package com.example.animalese_typing.ui.settings

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun GeneralSettingsScreen() {
    SettingsList {
        SettingsCategory("General")
        SettingsItem(
            title = "Sound on keypress",
            control = { Switch(checked = true, onCheckedChange = {}) }
        )
        SettingsItem(
            title = "Vibration on keypress",
            control = { Switch(checked = false, onCheckedChange = {}) }
        )
        SliderItem(
            title = "Vibration Intensity",
            value = 0.3f,
            onValueChange = {},
            valueRange = 0.0f..1.0f
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun GeneralSettingsScreenPreview() {
    AnimaleseTypingTheme {
        GeneralSettingsScreen()
    }
}