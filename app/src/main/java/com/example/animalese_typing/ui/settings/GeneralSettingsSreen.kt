package com.example.animalese_typing.ui.settings

import androidx.compose.material3.Switch
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
fun GeneralSettingsScreen(
    preferences: AnimalesePreferences = AnimalesePreferences(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val currentVibrationIntensity by preferences.getVibrationIntensity().collectAsState(initial = 0.3f)

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
            value = currentVibrationIntensity,
            onValueChange = { scope.launch { preferences.saveVibrationIntensity(it) } },
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