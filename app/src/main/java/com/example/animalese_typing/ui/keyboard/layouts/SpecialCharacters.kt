package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object SpecialCharacters: Layout( listOf(
        listOf(
            Key.CharKey('1'), Key.CharKey('2'), Key.CharKey('3'), Key.CharKey('4'),
            Key.CharKey('5'), Key.CharKey('6'), Key.CharKey('7'), Key.CharKey('8'),
            Key.CharKey('9'), Key.CharKey('0')
        ),
        listOf(
            Key.CharKey('@'), Key.CharKey('#'), Key.CharKey('€'), Key.CharKey('_'),
            Key.CharKey('&'), Key.CharKey('-'), Key.CharKey('+'), Key.CharKey('('),
            Key.CharKey(')'), Key.CharKey('/')
        ),
        listOf(
            Key.TextKey("=\\", -4, 0.15f),
            Key.CharKey('*'), Key.CharKey('\"'), Key.CharKey('\''), Key.CharKey(':'),
            Key.CharKey(';'), Key.CharKey('!'), Key.CharKey('?'),
            Key.IconKey(R.drawable.ic_backspace, -5, isRepeatable = true)
        ),
        listOf(
            Key.TextKey("ABC", -3, 0.15f),
            Key.CharKey(',', 0.1f),
            Key.CharKey(' ', 0.5f, isRepeatable = true),
            Key.CharKey('.', 0.1f),
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, type = "highlight")
        )
    )
)

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 411, heightDp = 250)
@Composable
fun LayoutSpecialCharactersPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = SpecialCharacters
        )
    }
}