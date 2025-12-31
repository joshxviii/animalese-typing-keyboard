package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.AnimaleseKeyboard
import com.example.animalese_typing.ui.settings.SliderItem
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun VoiceEditor() {
    Row() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            SliderItem(
                title = "Volume",
                value = 0.5f,
                onValueChange = {},
            )
            SliderItem(
                title = "Pitch",
                value = 0.5f,
                onValueChange = {},
            )
            SliderItem(
                title = "Intonation",
                value = 0.5f,
                onValueChange = {},
            )
            SliderItem(
                title = "Variation",
                value = 0.5f,
                onValueChange = {},
            )
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun VoiceEditorPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Chocolate
    ) {
        AnimaleseKeyboard(
            currentContent = KeyboardContent.VOICE_EDITOR
        )
    }
}
// endregion