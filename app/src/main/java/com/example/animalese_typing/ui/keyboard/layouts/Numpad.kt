package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Numpad: Layout(listOf(
        listOf(
            Key.CharKey('+', 0.066f, type = "alt"), Key.CharKey('7'), Key.CharKey('8'), Key.CharKey('9'), Key.CharKey('.', 0.066f, type = "alt")
        ),
        listOf(
            Key.CharKey('-', 0.066f, type = "alt"), Key.CharKey('4'), Key.CharKey('5'), Key.CharKey('6'), Key.CharKey('.', 0.066f, type = "alt")
        ),
        listOf(
            Key.CharKey('*', 0.066f, type = "alt"), Key.CharKey('1'), Key.CharKey('2'), Key.CharKey('3'), Key.CharKey('.', 0.066f, type = "alt")
        ),
        listOf(
            Key.TextKey("ABC", -2, 0.2125f, type = "alt"),
            Key.CharKey(',', 0.125f, type = "alt"),
            Key.TextKey("!?#", -2, 0.175f),
            Key.CharKey('0', 0.325f),
            Key.CharKey('=', 0.175f),
            Key.CharKey('.', 0.125f, type = "alt"),
            Key.IconKey(R.drawable.ic_enter, 10, 0.2125f, type = "highlight")
        )
    )
)

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutNumpadPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = Numpad
        )
    }
}