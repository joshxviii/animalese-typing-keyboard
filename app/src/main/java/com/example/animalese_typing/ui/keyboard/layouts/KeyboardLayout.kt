package com.example.animalese_typing.ui.keyboard.layouts

import com.example.animalese_typing.ui.keyboard.Key

enum class KeyboardLayouts(val keyboardLayout: KeyboardLayout) {
    QWERTY(Qwerty),
    QWERTZ(Qwertz),
    AZERTY(Azerty),
    NUMPAD(Numpad),
    SPECIAL(SpecialCharacters),
    SPECIAL_ALT(SpecialCharactersAlt)
}
sealed class KeyboardLayout(
    val value: List<List<Key>>
)