package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Qwertz: Layout(listOf(
        listOf(
            Key.CharKey('q', subChars=listOf('A')),
			Key.CharKey('w'),
			Key.CharKey('e'),
            Key.CharKey('r'),
			Key.CharKey('t'),
			Key.CharKey('z'),
            Key.CharKey('u'),
			Key.CharKey('i'),
			Key.CharKey('o'),
            Key.CharKey('p')
        ),
        listOf(
            Key.Empty(),
			Key.CharKey('a'),
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
            Key.CharKey('y'),
			Key.CharKey('x'),
			Key.CharKey('c'),
            Key.CharKey('v'),
			Key.CharKey('b'),
			Key.CharKey('n'),
            Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace,0.16f,"alt",isRepeatable = true,event=KeyFunctions.BACKSPACE)
        ),
        listOf(
            Key.TextKey("123", 0.15f,"alt", event=KeyFunctions.OPEN_NUMPAD),
            Key.CharKey(',',0.1f,"alt"),
            Key.CharKey(' ',0.5f, isRepeatable = true),
            Key.CharKey('.',0.1f,"alt"),
            Key.IconKey(R.drawable.ic_enter,0.15f,"highlight", event=KeyFunctions.ENTER)
        )
    )
)

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutQwertzPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = Qwertz
        )
    }
}
// endregion