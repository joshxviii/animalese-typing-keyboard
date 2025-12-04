package com.example.animalese_typing.ui.settings

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.capitalizeAll
import kotlinx.coroutines.launch

@Composable
fun ThemesSettingsScreen(
    preferences: AnimalesePreferences = AnimalesePreferences(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val currentTheme by preferences.getTheme().collectAsState(initial = AnimaleseThemes.Light)
    val useSystemDefault by preferences.getSystemDefaultTheme().collectAsState(initial = true)

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
        AnimaleseThemes.themes.keys.forEach { themeName ->
            RadioButtonItem(
                title = themeName.capitalizeAll(),
                selected = currentTheme == AnimaleseThemes.fromName(themeName),
                onClick = { scope.launch { preferences.saveTheme(themeName) } },
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