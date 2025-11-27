package com.example.animalese_typing.ui.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Chocolate
import com.example.animalese_typing.ui.theme.Cream
import com.example.animalese_typing.ui.theme.Dark
import com.example.animalese_typing.ui.theme.Light
import kotlinx.coroutines.launch

@Composable
fun ThemesSettingsScreen(
    preferences: AnimalesePreferences = AnimalesePreferences(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val currentTheme by preferences.getTheme().collectAsState(initial = Light)
    val useSystemDefault by preferences.getSystemDefaultTheme().collectAsState(initial = true)

    val themes = listOf(
        "Light" to Light,
        "Dark" to Dark,
        "Cream" to Cream,
        "Chocolate" to Chocolate
    )

    SettingsList {

        SettingsItem(
            title = "Use System Default",
            control = {
                Switch(checked = useSystemDefault, onCheckedChange = {
                    scope.launch { preferences.saveSystemDefaultTheme(it) }
                })
            }
        )

        SettingsCategory("Themes")
        themes.forEach { (themeName, themeValue) ->
            RadioButtonItem(
                title = themeName,
                selected = currentTheme == themeValue,
                onClick = { scope.launch { preferences.saveTheme(themeName.lowercase()) } },
                enabled = !useSystemDefault
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ThemesSettingsScreenPreview() {
    AnimaleseTypingTheme {
        ThemesSettingsScreen()
    }
}