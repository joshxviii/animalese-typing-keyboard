package com.example.animalese_typing.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Chocolate
import com.example.animalese_typing.ui.theme.Dark
import com.example.animalese_typing.ui.theme.Latte
import com.example.animalese_typing.ui.theme.Light
import kotlinx.coroutines.launch

@Composable
fun ThemesSettingsScreen(
    preferences: AnimalesePreferences = AnimalesePreferences(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val currentTheme by preferences.getTheme().collectAsState(initial = Light)

    SettingsList {
        RadioButtonItem(
            title = "Light",
            selected = currentTheme == Light,
            onClick = { scope.launch { preferences.saveTheme("light") } }
        )
        RadioButtonItem(
            title = "Dark",
            selected = currentTheme == Dark,
            onClick = { scope.launch { preferences.saveTheme("dark") } }
        )
        RadioButtonItem(
            title = "Latte",
            selected = currentTheme == Latte,
            onClick = { scope.launch { preferences.saveTheme("latte") } }
        )
        RadioButtonItem(
            title = "Chocolate",
            selected = currentTheme == Chocolate,
            onClick = { scope.launch { preferences.saveTheme("chocolate") } }
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ThemesSettingsScreenPreview() {
    AnimaleseTypingTheme {
        ThemesSettingsScreen()
    }
}