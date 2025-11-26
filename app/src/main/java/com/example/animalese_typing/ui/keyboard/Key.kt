package com.example.animalese_typing.ui.keyboard

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Data model for keyboard keys
sealed class Key(
    val code: Int?,
    val weight: Float,
    val isRepeatable: Boolean = false,
    val type : String,
    val data : String?
) {
    class Empty(
        weight: Float = 0.05f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(null, weight, isRepeatable, type, data)
    class CharKey(
        val char: Char,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(char.code, weight, isRepeatable, type, data)
    class IconKey(
        val id: Int,
        code: Int = -1,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        val size: Dp = 32.dp,
        type : String = "",
        data : String? = null
    ) : Key(code, weight, isRepeatable, type, data)
    class TextKey(
        val text: String,
        code: Int? = null,
        weight: Float = 0.1f,
        isRepeatable: Boolean = false,
        type : String = "",
        data : String? = null
    ) : Key(code, weight, isRepeatable, type, data)

    override fun toString(): String {
        return when (this) {
            is Empty -> "Empty"
            is CharKey -> "CharKey($char)"
            is IconKey -> "IconKey($id)"
            is TextKey -> "TextKey($text)"
        }
    }
}