package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun TopBarEditMenu() {
    Text("Edit Menu")
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun TopBarEditMenuPreview() {
    AnimaleseTypingTheme {
        KeyboardView(
            currentContent = KeyboardContent.TOPBAR_MENU
        )
    }
}
// endregion