package com.example.animalese_typing.ui.keyboard

enum class KeyFunctions {
    NONE,
    CHARACTER,
    SPACE,
    ENTER,
    BACKSPACE,
    SHIFT,
    OPEN_NUMPAD,
    OPEN_KEYPAD,
    OPEN_SPECIAL,
    OPEN_SPECIAL_ALT
}

/**
 * Data model for the keyboard keys
 */
sealed class Key(
    val weight: Float,
    val type : String,
    val isRepeatable: Boolean = false,
    val event : KeyFunctions,
    ) {
    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false,
        event : KeyFunctions = KeyFunctions.NONE,
    ) : Key(weight, "", isRepeatable, event)
    class Blank(
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        event : KeyFunctions = KeyFunctions.NONE,
    ) : Key(weight, "", isRepeatable, event)
    class CharKey(
        val char: Char,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        val showPopup: Boolean = true,
        val altKeyHint: Boolean = false,
        val subChars: List<Char> = emptyList(),
        event : KeyFunctions = KeyFunctions.CHARACTER,
    ) : Key(weight, type, isRepeatable, event) {
        var isUpperCase: Boolean = false
        var selectSubChar: Int? = null
        val charToCommit: CharSequence
            get() {// if sub character is selected get that character, otherwise get the key's default char. then apply casing.
                val subChar = selectSubChar
                selectSubChar = null // reset
                return (subChar?.let { subChars[it] } ?: char)
                    .let { if (isUpperCase) it.uppercase() else it.lowercase() }
            }
    }
    class IconKey(
        val iconId: Int,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        event : KeyFunctions = KeyFunctions.NONE,
    ) : Key(weight, type, isRepeatable, event)
    class TextKey(
        val text: String,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        event : KeyFunctions = KeyFunctions.NONE,
    ) : Key(weight, type, isRepeatable, event)

    override fun toString(): String {
        return when (this) {
            is Empty -> "Empty"
            is CharKey -> "${char.uppercase()} $event"
            else -> "$event"
        }
    }
}