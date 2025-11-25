package com.example.animalese_typing.ui.keyboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

// Defining Keyboard layouts
object KeyboardLayouts {
    // qwerty keyboard
    val Qwerty = listOf(
        listOf(
            Key.TextKey("q"), Key.TextKey("w"), Key.TextKey("e"),
            Key.TextKey("r"), Key.TextKey("t"), Key.TextKey("y"),
            Key.TextKey("u"), Key.TextKey("i"), Key.TextKey("o"),
            Key.TextKey("p")
        ),
        listOf(
            Key.Empty(), Key.TextKey("a"), Key.TextKey("s"), Key.TextKey("d"),
            Key.TextKey("f"), Key.TextKey("g"), Key.TextKey("h"),
            Key.TextKey("j"), Key.TextKey("k"), Key.TextKey("l"), Key.Empty()
        ),
        listOf(
            Key.SpecialTextKey("↑", -1, 0.15f),
            Key.TextKey("z"), Key.TextKey("x"), Key.TextKey("c"),
            Key.TextKey("v"), Key.TextKey("b"), Key.TextKey("n"),
            Key.TextKey("m"),
            Key.IconKey(Icons.Filled.ArrowBack, "Backspace", -5, 0.15f, isRepeatable = true)
        ),
        listOf(
            Key.SpecialTextKey("?123", -2, 0.15f),
            Key.TextKey(",",0.1f),
            Key.TextKey(" ",0.5f, isRepeatable = true),
            Key.TextKey(".",0.1f),
            Key.SpecialTextKey("Enter", 10, 0.15f)
        )
    )

    // azerty keyboard
    val Azerty = listOf(
        listOf(
            Key.TextKey("a"), Key.TextKey("z"), Key.TextKey("e"), Key.TextKey("r"),
            Key.TextKey("t"), Key.TextKey("y"), Key.TextKey("u"), Key.TextKey("i"),
            Key.TextKey("o"), Key.TextKey("p")
        ),
        listOf(
            Key.Empty(), Key.TextKey("q"), Key.TextKey("s"), Key.TextKey("d"),
            Key.TextKey("f"), Key.TextKey("g"), Key.TextKey("h"), Key.TextKey("j"),
            Key.TextKey("k"), Key.TextKey("l"), Key.Empty()
        ),
        listOf(
            Key.SpecialTextKey("↑", -1, 0.15f),
            Key.TextKey("w"), Key.TextKey("x"), Key.TextKey("c"), Key.TextKey("v"),
            Key.TextKey("b"), Key.TextKey("n"), Key.TextKey("m"),
            Key.IconKey(Icons.Filled.ArrowBack, "Backspace", -5, 0.15f, isRepeatable = true)
        ),
        listOf(
            Key.SpecialTextKey("?123", -2, 0.15f),
            Key.TextKey(",", 0.1f),
            Key.TextKey(" ", 0.5f, isRepeatable = true),
            Key.TextKey(".", 0.1f),
            Key.SpecialTextKey("Enter", 10, 0.15f)
        )
    )

    // special characters
    val SpecialCharacters = listOf(
        listOf(
            Key.TextKey("1"), Key.TextKey("2"), Key.TextKey("3"), Key.TextKey("4"),
            Key.TextKey("5"), Key.TextKey("6"), Key.TextKey("7"), Key.TextKey("8"),
            Key.TextKey("9"), Key.TextKey("0")
        ),
        listOf(
            Key.TextKey("@"), Key.TextKey("#"), Key.TextKey("€"), Key.TextKey("_"),
            Key.TextKey("&"), Key.TextKey("-"), Key.TextKey("+"), Key.TextKey("("),
            Key.TextKey(")"), Key.TextKey("/")
        ),
        listOf(
            Key.SpecialTextKey("=\\", -4, 0.15f),
            Key.TextKey("*"), Key.TextKey("\""), Key.TextKey("'"), Key.TextKey(":"),
            Key.TextKey(";"), Key.TextKey("!"), Key.TextKey("?"),
            Key.IconKey(Icons.Filled.ArrowBack, "Backspace", -5, 0.15f, isRepeatable = true)
        ),
        listOf(
            Key.SpecialTextKey("ABC", -3, 0.15f),
            Key.TextKey(",", 0.1f),
            Key.TextKey(" ", 0.5f, isRepeatable = true),
            Key.TextKey(".", 0.1f),
            Key.SpecialTextKey("Enter", 10, 0.15f)
        )
    )

}