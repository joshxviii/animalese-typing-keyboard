package com.example.animalese_typing.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun ThemesSettingsScreen() {
    SettingsList {
        SettingsCategory("Themes")
        RadioButtonItem(title = "Light", selected = true, onClick = {})
        RadioButtonItem(title = "Dark", selected = false, onClick = {})
        RadioButtonItem(title = "Latte", selected = false, onClick = {})
        RadioButtonItem(title = "Chocolate", selected = false, onClick = {})
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ThemesSettingsScreenPreview() {
    AnimaleseTypingTheme {
        ThemesSettingsScreen()
    }
}