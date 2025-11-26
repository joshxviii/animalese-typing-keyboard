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
import com.example.animalese_typing.ui.keyboard.KeyboardScreen
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Chocolate
import com.example.animalese_typing.ui.theme.Dark
import com.example.animalese_typing.ui.theme.Latte
import com.example.animalese_typing.ui.theme.Light

@Preview(showBackground = true, widthDp = (411*2)+20, heightDp = (250*2)+20)
@Composable
fun ThemePreview() {
    Row(
        modifier = Modifier.background(color=Color.Black),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimaleseTypingTheme(
                theme = Light
            ) { KeyboardScreen() }

            AnimaleseTypingTheme(
                theme = Latte
            ) { KeyboardScreen() }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimaleseTypingTheme(
                theme = Dark
            ) { KeyboardScreen() }

            AnimaleseTypingTheme(
                theme = Chocolate
            ) { KeyboardScreen() }
        }
    }
}