package com.example.animalese_typing.ui.keyboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.Highlight

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
            Key.IconKey(R.drawable.ic_shift_lock),
            Key.CharKey('z'), Key.CharKey('x'), Key.CharKey('c'),
            Key.CharKey('v'), Key.CharKey('b'), Key.CharKey('n'),
            Key.CharKey('m'),
            Key.IconKey(R.drawable.ic_backspace, isRepeatable = true)
        ),
        listOf(
            Key.TextKey("?123", -2, 0.15f),
            Key.CharKey(',',0.1f),
            Key.CharKey(' ',0.5f, isRepeatable = true),
            Key.CharKey('.',0.1f),
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, colors = KeyColors(base = Highlight, label = Color.White))
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
            Key.IconKey(R.drawable.ic_backspace, isRepeatable = true)
        ),
        listOf(
            Key.TextKey("?123", -2, 0.15f),
            Key.CharKey(',', 0.1f),
            Key.CharKey(' ', 0.5f, isRepeatable = true),
            Key.CharKey('.', 0.1f),
            Key.IconKey(R.drawable.ic_enter, 10,0.15f, colors = KeyColors(base = Highlight, label = Color.White))
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
            Key.IconKey(R.drawable.ic_backspace, isRepeatable = true)
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