package com.example.animalese_typing.ui.keyboard
import com.example.animalese_typing.KeyFunctionIds

// Data model for keyboard keys
sealed class Key(
    val weight: Float,
    val isRepeatable: Boolean = false,
    val type : String,
    val event : KeyFunctionIds
) {
    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false,
        event : KeyFunctionIds = KeyFunctionIds.NONE
    ) : Key(weight, isRepeatable, "", event)
    class CharKey(
        val char: Char,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        event : KeyFunctionIds = KeyFunctionIds.CHARACTER
    ) : Key(weight, isRepeatable, type, event)
    class IconKey(
        val iconId: Int,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        event : KeyFunctionIds = KeyFunctionIds.NONE
    ) : Key(weight, isRepeatable, type, event)
    class TextKey(
        val text: String,
        weight: Float = 0.1f,
        type : String = "",
        isRepeatable: Boolean = false,
        event : KeyFunctionIds = KeyFunctionIds.NONE
    ) : Key(weight, isRepeatable, type, event)

    override fun toString(): String {
        return when (this) {
            is Empty -> "Empty"
            is CharKey -> "CharKey($char)"
            is IconKey -> "IconKey($iconId)"
            is TextKey -> "TextKey($text)"
        }
    }
}