package com.example.animalese_typing.ui.keyboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.IntSize

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
    var size: IntSize = IntSize(0, 0)
    var coordinates: Offset = Offset(0f, 0f) // from center of key
        get() {
            return Offset(
                x = field.x + (size.width / 2),
                y = field.y - (size.height / 2)
            )
        }

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
        val subChars: List<Char> = emptyList(),
        event : KeyFunctions = KeyFunctions.CHARACTER,
    ) : Key(weight, type, isRepeatable, event) {
        var isUpperCase: Boolean = false
        val finalChar: Char
            get() = if (isUpperCase) char.uppercaseChar() else char.lowercaseChar()
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
            is CharKey -> "${char.uppercase()} $event at (${coordinates.x}, ${coordinates.y}) size: $size"
            else -> "$event at (${coordinates.x}, ${coordinates.y}) size: $size"
        }
    }
}