package com.example.animalese_typing.ui.keyboard.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.KeyFunctionIds
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

object Azerty: Layout(listOf(
        listOf(
            Key.CharKey('a'), Key.CharKey('z'), Key.CharKey('e'), Key.CharKey('r'),
            Key.CharKey('t'), Key.CharKey('y'), Key.CharKey('u'), Key.CharKey('i'),
            Key.CharKey('o'), Key.CharKey('p')
        ),
        listOf(
            Key.Empty(), Key.CharKey('q'), Key.CharKey('s'), Key.CharKey('d'),
            Key.CharKey('f'), Key.CharKey('g'), Key.CharKey('h'), Key.CharKey('j'),
            Key.CharKey('k'), Key.CharKey('l'), Key.Empty()
        ),
        listOf(
            Key.IconKey(R.drawable.ic_shift_off, 0.16f, type = "alt", event=KeyFunctionIds.SHIFT),
            Key.CharKey('w'), Key.CharKey('x'), Key.CharKey('c'), Key.CharKey('v'),
            Key.CharKey('b'), Key.CharKey('n'), Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace, isRepeatable = true, weight = 0.16f, type = "alt", event=KeyFunctionIds.BACKSPACE)
        ),
        listOf(
            Key.TextKey("123", 0.15f, type = "alt", event=KeyFunctionIds.OPEN_NUMPAD),
            Key.CharKey(',',0.1f, type = "alt"),
            Key.CharKey(' ',0.5f, isRepeatable = true),
            Key.CharKey('.',0.1f, type = "alt"),
            Key.IconKey(R.drawable.ic_enter,0.15f, type = "highlight", event=KeyFunctionIds.ENTER)
        )
    )
)

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 411, heightDp = 218)
@Composable
fun LayoutAzertyPreview() {
    AnimaleseTypingTheme {
        KeyboardLayout(
            layout = Azerty
        )
    }
}