package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object SpecialCharactersAlt: Layout( listOf(
        listOf(
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.CharKey('.'),
			Key.CharKey('.')
        ),
        listOf(
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.CharKey('.')
        ),
        listOf(
            Key.TextKey("123", 0.15f,"alt", event=KeyFunctions.OPEN_SPECIAL),
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.CharKey('.'),
			Key.CharKey('.'),
			Key.CharKey('.'),
            Key.IconKey(R.drawable.ic_backspace,0.15f,"alt",isRepeatable = true)
        ),
        listOf(
            Key.TextKey("ABC", 0.15f,"alt", event=KeyFunctions.OPEN_KEYPAD),
            Key.CharKey(',', 0.1f,"alt"),
            Key.CharKey(' ', 0.5f, isRepeatable = true, showPopup = false),
            Key.CharKey('.', 0.1f,"alt"),
            Key.IconKey(R.drawable.ic_enter,0.15f,"highlight", event=KeyFunctions.ENTER)
        )
    )
)

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutSpecialCharactersAltPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = SpecialCharactersAlt
        )
    }
}
// endregion