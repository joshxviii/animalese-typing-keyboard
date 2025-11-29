package com.example.animalese_typing.ui.keyboard.layouts

import com.example.animalese_typing.ui.keyboard.Key

enum class KeyboardLayouts(val layout: Layout) {
    QWERTY(Qwerty),
    QWERTZ(Qwertz),
    AZERTY(Azerty),
    NUMPAD(Numpad),
    SPECIAL(SpecialCharacters),
    SPECIAL_ALT(SpecialCharactersAlt)
}
sealed class Layout(
    val value: List<List<Key>>
)