package com.example.animalese_typing.ui.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

/**
 * If [Key.CharKey.subChars] is not empty [KeyPopoutMenu] will render
 * above the key when held down.
 */
@Composable
fun KeyPopoutMenu(
    chars: List<Char>
) {

}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun KeyPopoutMenuPreview() {
    AnimaleseTypingTheme {
        KeyPopoutMenu(chars = listOf('a', 'b', 'c', 'd', 'e'))
    }
}
// endregion