package com.example.animalese_typing.ui.ui_tests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

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
                theme = AnimaleseThemes.Light
            ) { KeyboardView() }

            AnimaleseTypingTheme(
                theme = AnimaleseThemes.Dark
            ) { KeyboardView(currentLayout = KeyboardLayouts.NUMPAD) }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            AnimaleseTypingTheme(
                theme = AnimaleseThemes.Cream
            ) { KeyboardView(currentLayout = KeyboardLayouts.NUMPAD) }

            AnimaleseTypingTheme(
                theme = AnimaleseThemes.Chocolate
            ) { KeyboardView() }
        }
    }
}