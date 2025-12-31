package com.example.animalese_typing.ui.keyboard.layouts

import android.view.ContextThemeWrapper
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.AnimaleseKeyboard
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme

@Composable
fun EmojiPicker(
    onEmojiPicked: (String) -> Unit = {},
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {}
) {
    Box {
        val currentTheme = Theme.colors
        val backKey = Key.IconKey(
            iconId=R.drawable.ic_backspace,
            type="alt",
            isRepeatable=true,
            event=KeyFunctions.BACKSPACE
        )

        //TODO: make custom emoji picker ui so custom emoji fonts can be used.
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
            },
            update = {}
        )
        FloatingActionButton(
            modifier = Modifier
                .alpha(0.7f)
                .padding(18.dp)
                .size(42.dp)
                .align(Alignment.BottomEnd)
                .pointerInput(backKey) {
                    awaitEachGesture {
                        awaitFirstDown(requireUnconsumed = false)
                        onKeyDown(backKey)

                        waitForUpOrCancellation()
                        onKeyUp(backKey)
                    }
                },
            onClick = {},
            containerColor = Theme.colors.iconButton,
            contentColor = Theme.colors.background,
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(R.drawable.ic_backspace),
                contentDescription = "",
            )
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun EmojiLayoutPreview() {
    AnimaleseTypingTheme {
        AnimaleseKeyboard(
            currentContent = KeyboardContent.EMOJI_PICKER
        )
    }
}
// endregion