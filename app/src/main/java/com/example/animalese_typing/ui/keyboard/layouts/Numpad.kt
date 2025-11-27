package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Numpad: Layout(listOf(
        listOf(
            Key.CharKey('+', 0.066f,"alt"),
			Key.CharKey('7'),
			Key.CharKey('8'),
			Key.CharKey('9'),
			Key.CharKey('*', 0.066f,"alt")
        ),
        listOf(
            Key.CharKey('-', 0.066f,"alt"),
			Key.CharKey('4'),
			Key.CharKey('5'),
			Key.CharKey('6'),
			Key.CharKey('/', 0.066f,"alt")
        ),
        listOf(
            Key.CharKey('%', 0.066f,"alt"),
			Key.CharKey('1'),
			Key.CharKey('2'),
			Key.CharKey('3'),
			Key.IconKey(R.drawable.ic_backspace, 0.066f,"alt", isRepeatable = true, event=KeyFunctions.BACKSPACE)
        ),
        listOf(
            Key.TextKey("ABC", 0.2125f,"alt", event=KeyFunctions.OPEN_KEYPAD),
            Key.CharKey(',',0.125f,"alt"),
            Key.TextKey("!?#",0.175f, event= KeyFunctions.OPEN_SPECIAL),
            Key.CharKey('0',0.325f),
            Key.CharKey('=',0.175f),
            Key.CharKey('.',0.125f,"alt"),
            Key.IconKey(R.drawable.ic_enter, 0.2125f,"highlight", event=KeyFunctions.ENTER)
        )
    )
)

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutNumpadPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = Numpad
        )
    }
}
// endregion