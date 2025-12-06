package com.example.animalese_typing.ui.keyboard.layouts

import com.example.animalese_typing.ui.keyboard.Key

enum class KeyLayouts(val keyLayout: KeyLayout) {
    QWERTY(Qwerty),
    QWERTZ(Qwertz),
    AZERTY(Azerty),
    NUMPAD(Numpad),
    SPECIAL(SpecialCharacters),
    SPECIAL_ALT(SpecialCharactersAlt)
}
sealed class KeyLayout(
    val value: List<List<Key>>
)