package com.example.animalese_typing.ui.keyboard

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.KeyFunctionIds

// Data model for keyboard keys
sealed class Key(
    val weight: Float,
    val isRepeatable: Boolean = false,
    val type : String,
    val function : KeyFunctionIds?
) {
    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false,
        type : String = "",
        function : KeyFunctionIds? = null
    ) : Key(weight, isRepeatable, type, function)
    class CharKey(
        val char: Char,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        function : KeyFunctionIds? = null
    ) : Key(weight, isRepeatable, type, function)
    class IconKey(
        val id: Int,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        val size: Dp = 32.dp,
        type : String = "",
        function : KeyFunctionIds? = null
    ) : Key(weight, isRepeatable, type, function)
    class TextKey(
        val text: String,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        function : KeyFunctionIds? = null
    ) : Key(weight, isRepeatable, type, function)

    override fun toString(): String {
        return when (this) {
            is Empty -> "Empty"
            is CharKey -> "CharKey($char)"
            is IconKey -> "IconKey($id)"
            is TextKey -> "TextKey($text)"
        }
    }
}