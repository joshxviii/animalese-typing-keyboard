package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.AnimaleseKeyboard
import com.example.animalese_typing.ui.keyboard.TopBarButtons
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme

@Composable
fun TopBarEditMenu(
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

    }
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun TopBarEditMenuPreview() {
    AnimaleseTypingTheme {
        AnimaleseKeyboard(
            currentContent = KeyboardContent.TOPBAR_MENU
        )
    }
}
// endregion