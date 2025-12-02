package com.example.animalese_typing.utils

import android.content.Context

/**
 * Responsible for managing text prediction, autofill, n-gram, etc.
 */
class TextPredictionManager(context: Context) {

    fun getSuggestions(prefix: String): List<String> {
        if (prefix.isEmpty()) return emptyList()

        // TODO get suggestions
        return emptyList()
    }
}