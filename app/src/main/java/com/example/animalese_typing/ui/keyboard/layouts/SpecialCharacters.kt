package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object SpecialCharacters: KeyboardLayout( listOf(
        listOf(
            Key.CharKey('1', subChars = listOf('½', '⅓', '¼', '⅕', '⅙', '⅐', '⅛', '⅑', '⅒') ),
			Key.CharKey('2'),
			Key.CharKey('3'),
			Key.CharKey('4'),
            Key.CharKey('5'),
			Key.CharKey('6'),
			Key.CharKey('7'),
			Key.CharKey('8'),
            Key.CharKey('9'),
			Key.CharKey('0')
        ),
        listOf(
            Key.CharKey('@'),
			Key.CharKey('#'),
			Key.CharKey('$', subChars = listOf('¢', '₹', '£', '¥', '€', '₱')),
			Key.CharKey('_'),
            Key.CharKey('&'),
			Key.CharKey('-'),
			Key.CharKey('+'),
			Key.CharKey('(', subChars = listOf('[', '{', '<')),
            Key.CharKey(')', subChars = listOf(']', '}', '>')),
			Key.CharKey('/')
        ),
        listOf(
            Key.TextKey("=\\<",0.15f,"alt", event=KeyFunctions.OPEN_SPECIAL_ALT),
            Key.CharKey('*'),
			Key.CharKey('\"'),
			Key.CharKey('\''),
			Key.CharKey(':'),
            Key.CharKey(';'),
			Key.CharKey('!', subChars = listOf('¡')),
			Key.CharKey('?', subChars = listOf('¿')),
            Key.IconKey(R.drawable.ic_backspace,0.15f,"alt", isRepeatable = true, event=KeyFunctions.BACKSPACE)
        ),
        listOf(
            Key.TextKey("ABC", 0.15f,"alt", event=KeyFunctions.OPEN_KEYPAD),
            Key.CharKey(',', 0.1f,"alt"),
            Key.Blank(0.5f, event=KeyFunctions.SPACE),
            Key.CharKey('.', 0.1f,"alt"),
            Key.IconKey(R.drawable.ic_enter,0.15f,"highlight", event=KeyFunctions.ENTER)
        )
    )
)

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutSpecialCharactersPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            keyboardLayout = SpecialCharacters
        )
    }
}
// endregion