package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme

enum class TopBarButtons(
    val icon: Int,
) {
    SETTINGS(R.drawable.ic_settings),
    RESIZE(R.drawable.ic_keyboard_resize),
    VOICE_EDITOR(R.drawable.ic_note),
    EMOJI_PICKER(R.drawable.male_full),
    CLIPBOARD(R.drawable.ic_clipboard),
}


/**
 * The menu bar above the main keyboard.
 */
@Composable
fun TopBar(
    onSettingsClick: () -> Unit = {},
    onResizeClick: (Boolean) -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
    onVoiceEditorClick: () -> Unit = {},
    onEmojiPickerClick: () -> Unit = {},
    onClipboardClick: () -> Unit = {},
    onBackToKeyboardClick: () -> Unit = {},
    onTopBarMenuClick: () -> Unit = {},
    showSuggestions: Boolean = false,
    showBackToKeyboardButton: Boolean = false,
    shownButtons: List<TopBarButtons> = listOf(
        TopBarButtons.VOICE_EDITOR,
        TopBarButtons.EMOJI_PICKER,
        TopBarButtons.SETTINGS,
        TopBarButtons.RESIZE
    ),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 8.dp)
            .background(color = Theme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showSuggestions) {
            TextSuggestBar(
                //TODO: wtf is a Radix Trie...
                // Learn what it is and build/acquire one for this
                suggestions = listOf("animalese", "test", "keyboard", "more"), // TODO: get suggestions from Trie
                modifier =  Modifier.weight(1f),
                onSuggestionClick = onSuggestionClick
            )
        }
        else {
            // back to keyboard button
            if (showBackToKeyboardButton) TopBarButton(
                icon = R.drawable.ic_keyboard,
                onClick = onBackToKeyboardClick,
                modifier = Modifier
            )
            else Spacer(modifier = Modifier.size(24.dp))

            // top bar buttons
            if (shownButtons.isNotEmpty()) for (button in shownButtons) {
                TopBarButton(
                    icon = button.icon,
                    onClick ={
                        when (button) {
                            TopBarButtons.SETTINGS -> onSettingsClick()
                            TopBarButtons.RESIZE -> onResizeClick(true)
                            TopBarButtons.VOICE_EDITOR -> onVoiceEditorClick()
                            TopBarButtons.EMOJI_PICKER -> onEmojiPickerClick()
                            TopBarButtons.CLIPBOARD -> onClipboardClick()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(16.dp, 0.dp)
                )
            }
            else Spacer(modifier = Modifier.weight(1f).padding(16.dp, 0.dp))
        }

        // edit menu button
        TopBarButton(
            icon = R.drawable.ic_elipsis,
            onClick = onTopBarMenuClick,
            modifier = Modifier
        )
    }
}

@Composable
fun TopBarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = Theme.colors.iconButton,
    icon: Int
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(24.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = color
        )
    }
}

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 490)
@Composable
fun TopBarPreview() {
    AnimaleseTypingTheme {
        TopBar()
    }
}
// endregion