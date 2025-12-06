package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardKeyLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Azerty: KeyLayout(listOf(
        listOf(
            Key.CharKey('a', altKeyHint = true, subChars = listOf('1', 'á', 'à', 'â', 'ã', 'ä', 'æ')),
			Key.CharKey('z', altKeyHint = true, subChars = listOf('2')),
			Key.CharKey('e', altKeyHint = true, subChars = listOf('3', 'é', 'è', 'ê')),
			Key.CharKey('r', altKeyHint = true, subChars = listOf('4')),
            Key.CharKey('t', altKeyHint = true, subChars = listOf('5')),
			Key.CharKey('y', altKeyHint = true, subChars = listOf('6')),
            Key.CharKey('u', altKeyHint = true, subChars = listOf('7', 'ù', 'ú')),
            Key.CharKey('i', altKeyHint = true, subChars = listOf('8', 'ì', 'í')),
            Key.CharKey('o', altKeyHint = true, subChars = listOf('9', 'ò', 'ó')),
            Key.CharKey('p', altKeyHint = true, subChars = listOf('0'))
        ),
        listOf(
            Key.Empty(),
			Key.CharKey('q'),
			Key.CharKey('s'),
			Key.CharKey('d'),
            Key.CharKey('f'),
			Key.CharKey('g'),
			Key.CharKey('h'),
			Key.CharKey('j'),
            Key.CharKey('k'),
			Key.CharKey('l'),
			Key.Empty()
        ),
        listOf(
            Key.IconKey(R.drawable.ic_shift_off, 0.16f,"alt", event=KeyFunctions.SHIFT),
            Key.CharKey('w'),
			Key.CharKey('x'),
			Key.CharKey('c'),
			Key.CharKey('v'),
            Key.CharKey('b'),
			Key.CharKey('n', subChars = listOf('ñ')),
			Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace,0.16f,"alt", isRepeatable = true, event=KeyFunctions.BACKSPACE)
        ),
        listOf(
            Key.TextKey("123", 0.15f,"alt", event=KeyFunctions.OPEN_NUMPAD),
            Key.CharKey(',',0.1f,"alt"),
            Key.Blank(0.5f, event=KeyFunctions.SPACE),
            Key.CharKey('.',0.1f,"alt"),
            Key.IconKey(R.drawable.ic_enter,0.15f,"highlight", event=KeyFunctions.ENTER)
        )
    )
)

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutAzertyPreview() {
    AnimaleseTypingTheme {
        KeyboardKeyLayout(
            keyLayout = Azerty
        )
    }
}
// endregion