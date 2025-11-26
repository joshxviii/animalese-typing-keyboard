package com.example.animalese_typing.ui.keyboard

import androidx.compose.ui.graphics.Color
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.Highlight
import com.example.animalese_typing.ui.theme.KeyBaseAlt
import com.example.animalese_typing.ui.theme.KeyTextAlt

// Defining Keyboard layouts
object KeyboardLayouts {
    // qwerty keyboard
    val Qwerty = listOf(
        listOf(
            Key.CharKey('q'), Key.CharKey('w'), Key.CharKey('e'),
            Key.CharKey('r'), Key.CharKey('t'), Key.CharKey('y'),
            Key.CharKey('u'), Key.CharKey('i'), Key.CharKey('o'),
            Key.CharKey('p')
        ),
        listOf(
            Key.Empty(), Key.CharKey('a'), Key.CharKey('s'), Key.CharKey('d'),
            Key.CharKey('f'), Key.CharKey('g'), Key.CharKey('h'),
            Key.CharKey('j'), Key.CharKey('k'), Key.CharKey('l'), Key.Empty()
        ),
        listOf(
            Key.IconKey(R.drawable.ic_shift_off, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.CharKey('z'), Key.CharKey('x'), Key.CharKey('c'),
            Key.CharKey('v'), Key.CharKey('b'), Key.CharKey('n'),
            Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace,-5, isRepeatable = true, colors = KeyColors(KeyBaseAlt, KeyTextAlt))
        ),
        listOf(
            Key.TextKey("?123", -2, 0.15f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.CharKey(',',0.1f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.CharKey(' ',0.5f, isRepeatable = true),
            Key.CharKey('.',0.1f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, colors = KeyColors(Highlight,Color.White))
        )
    )

    // azerty keyboard
    val Azerty = listOf(
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
            Key.IconKey(R.drawable.ic_shift_lock),
            Key.CharKey('w'), Key.CharKey('x'), Key.CharKey('c'), Key.CharKey('v'),
            Key.CharKey('b'), Key.CharKey('n'), Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace,-5, isRepeatable = true)
        ),
        listOf(
            Key.TextKey("?123", -2, 0.15f),
            Key.CharKey(',', 0.1f),
            Key.CharKey(' ', 0.5f, isRepeatable = true),
            Key.CharKey('.', 0.1f),
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, colors = KeyColors(Highlight, Color.White))
        )
    )

    // number pad
    val NumPad = listOf(
        listOf(
            Key.CharKey('+', 0.066f, colors = KeyColors( KeyBaseAlt, KeyTextAlt)), Key.CharKey('7'), Key.CharKey('8'), Key.CharKey('9'), Key.CharKey('.', 0.066f, colors = KeyColors(base = KeyBaseAlt,KeyTextAlt))
        ),
        listOf(
            Key.CharKey('-', 0.066f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)), Key.CharKey('4'), Key.CharKey('5'), Key.CharKey('6'), Key.CharKey('.', 0.066f, colors = KeyColors(base = KeyBaseAlt,KeyTextAlt))
        ),
        listOf(
            Key.CharKey('*', 0.066f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)), Key.CharKey('1'), Key.CharKey('2'), Key.CharKey('3'), Key.CharKey('.', 0.066f, colors = KeyColors(base = KeyBaseAlt,KeyTextAlt))
        ),
        listOf(
            Key.TextKey("ABC", -2, 0.2125f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.CharKey(',', 0.125f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.TextKey("!?#", -2, 0.175f),
            Key.CharKey('0', 0.325f),
            Key.CharKey('=', 0.175f),
            Key.CharKey('.', 0.125f, colors = KeyColors(KeyBaseAlt, KeyTextAlt)),
            Key.IconKey(R.drawable.ic_enter, 10, 0.2125f, colors = KeyColors(Highlight, Color.White))
        )
    )

    // special characters
    val SpecialCharacters = listOf(
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
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, colors = KeyColors(base = Highlight, label = Color.White))
        )
    )

}