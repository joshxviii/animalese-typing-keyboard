package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Numpad: KeyboardLayout(listOf(
        listOf(
            Key.CharKey('+', 0.066f,"alt", showPopup = false),
			Key.CharKey('7', showPopup = false),
			Key.CharKey('8', showPopup = false),
			Key.CharKey('9', showPopup = false),
			Key.CharKey('*', 0.066f,"alt", showPopup = false)
        ),
        listOf(
            Key.CharKey('-', 0.066f,"alt", showPopup = false),
			Key.CharKey('4', showPopup = false),
			Key.CharKey('5', showPopup = false),
			Key.CharKey('6', showPopup = false),
			Key.CharKey('/', 0.066f,"alt", showPopup = false)
        ),
        listOf(
            Key.CharKey('%', 0.066f,"alt", showPopup = false),
			Key.CharKey('1', showPopup = false),
			Key.CharKey('2', showPopup = false),
			Key.CharKey('3', showPopup = false),
			Key.IconKey(R.drawable.ic_backspace, 0.066f,"alt", isRepeatable = true, event=KeyFunctions.BACKSPACE)
        ),
        listOf(
            Key.TextKey("ABC", 0.2125f,"alt", event=KeyFunctions.OPEN_KEYPAD),
            Key.CharKey(',',0.125f,"alt", showPopup = false),
            Key.TextKey("!?#",0.175f, event= KeyFunctions.OPEN_SPECIAL),
            Key.CharKey('0',0.325f, showPopup = false),
            Key.CharKey('=',0.175f, showPopup = false),
            Key.CharKey('.',0.125f,"alt", showPopup = false),
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
            keyboardLayout = Numpad
        )
    }
}
// endregion