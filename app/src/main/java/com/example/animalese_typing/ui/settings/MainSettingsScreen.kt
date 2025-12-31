package com.example.animalese_typing.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun MainSettingsScreen(navController: NavController? = null) {
    SettingsList {

        SettingsCategory(title = "Settings")
        SettingsItem(
            title = "General Settings",
            onClick = { navController?.navigate("general") }
        )
        SettingsItem(
            title = "Themes",
            onClick = { navController?.navigate("themes") }
        )
        SettingsItem(
            title = "Keyboard Layout",
            onClick = { navController?.navigate("layout") }
        )

        SettingsCategory(title = "About")
        SettingsItem(title = "Version", subtitle = "")
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingsScreenPreview() {
    AnimaleseTypingTheme {
        MainSettingsScreen()
    }
}