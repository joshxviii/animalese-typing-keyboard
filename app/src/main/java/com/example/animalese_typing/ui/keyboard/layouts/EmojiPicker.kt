package com.example.animalese_typing.ui.keyboard.layouts

import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.emoji2.emojipicker.RecentEmojiProvider
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme

@Composable
fun EmojiPicker(onEmojiPicked: (String) -> Unit = {}) {
    val currentTheme = Theme.colors
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // apply theme colors to emojipicker view
            val themeId = when (currentTheme) {
                AnimaleseThemes.Light -> R.style.Theme_AnimaleseTyping_light
                AnimaleseThemes.Dark -> R.style.Theme_AnimaleseTyping_dark
                AnimaleseThemes.Cream -> R.style.Theme_AnimaleseTyping_cream
                AnimaleseThemes.Chocolate -> R.style.Theme_AnimaleseTyping_chocolate
                AnimaleseThemes.Cherry -> R.style.Theme_AnimaleseTyping_cherry
                else -> R.style.Theme_AnimaleseTyping_light
            }
            val themedContext = ContextThemeWrapper(context, themeId)

            EmojiPickerView(themedContext).apply {
                emojiGridColumns = 9
                setOnEmojiPickedListener {
                    onEmojiPicked(it.emoji)
                }
            }
        }
    )
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun EmojiLayoutPreview() {
    AnimaleseTypingTheme {
        KeyboardView(
            currentContent = KeyboardContent.EMOJI_PICKER
        )
    }
}
// endregion