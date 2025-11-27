package com.example.animalese_typing.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.keyboard.layouts.Numpad
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Chocolate
import com.example.animalese_typing.ui.theme.Cream
import com.example.animalese_typing.ui.theme.Dark
import com.example.animalese_typing.ui.theme.Light

@Preview(showBackground = true, widthDp = (411*2)+20, heightDp = (250*2)+20)
@Composable
fun ThemePreview() {
    Row(
        modifier = Modifier.background(color=Color.Black),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            AnimaleseTypingTheme(
                theme = Light
            ) { KeyboardView() }

            AnimaleseTypingTheme(
                theme = Cream
            ) { KeyboardLayout(
                modifier = Modifier.weight(1f),
                layout = Numpad
            ) }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            AnimaleseTypingTheme(
                theme = Dark
            ) { KeyboardLayout(
                modifier = Modifier.weight(1f),
                layout = Numpad
            ) }

            AnimaleseTypingTheme(
                theme = Chocolate
            ) { KeyboardView() }
        }
    }
}