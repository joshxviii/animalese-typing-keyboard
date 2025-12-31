package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.AnimaleseIME
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.layouts.EmojiPicker
import com.example.animalese_typing.ui.keyboard.layouts.KeyLayout
import com.example.animalese_typing.ui.keyboard.layouts.KeyLayouts
import com.example.animalese_typing.ui.keyboard.layouts.TopBarEditMenu
import com.example.animalese_typing.ui.keyboard.layouts.VoiceEditor
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme

enum class KeyboardContent {
    KEYBOARD, EMOJI_PICKER, VOICE_EDITOR, TOPBAR_MENU
}

/**
 * The full keyboard view.
 */
@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    height: Float = 250f,
    currentContent: KeyboardContent = KeyboardContent.KEYBOARD,
    currentKeyLayout: KeyLayouts = KeyLayouts.QWERTY,
    onContentChange: (KeyboardContent) -> Unit = {},
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onResizeClick: (Boolean) -> Unit = {},
    onPointerMove: (Offset) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onTextToCommitClick: (String) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
    cursorActive: Boolean = false,
    showSuggestions: Boolean = false,
) {
    val navBarPadding = max(WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(), 0.dp)

    Box() {
        Column(
            modifier = modifier
                .height(height.dp + navBarPadding)
                .fillMaxWidth()
                .background(Theme.colors.background)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Top Bar
            TopBar(
                onSettingsClick = onSettingsClick,
                onResizeClick = {onResizeClick(true)},
                onSuggestionClick = onTextToCommitClick,
                showSuggestions = showSuggestions,
                onEmojiPickerClick = { onContentChange(KeyboardContent.EMOJI_PICKER) },
                onVoiceEditorClick = { onContentChange(KeyboardContent.VOICE_EDITOR) },
                onBackToKeyboardClick = { onContentChange(KeyboardContent.KEYBOARD) },
                onTopBarMenuClick = { onContentChange(KeyboardContent.TOPBAR_MENU) },
                showBackToKeyboardButton = currentContent != KeyboardContent.KEYBOARD
            )

            // Main content view
            Box(
                modifier = modifier
                    .padding(4.dp, 0.dp)
                    .weight(1f)
            ) {
                when (currentContent) {
                    KeyboardContent.KEYBOARD -> {
                        KeyLayouts.entries.forEach { e -> //TODO: Only load in necessary key layouts
                            if (currentKeyLayout == e) KeyboardKeyLayout(
                                keyLayout = e.keyLayout,
                                onKeyDown = onKeyDown,
                                onKeyUp = onKeyUp,
                                onPointerMove = onPointerMove,
                                shiftState = shiftState,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    KeyboardContent.EMOJI_PICKER -> EmojiPicker(
                        onTextToCommitClick,
                                onKeyDown,
                                onKeyUp
                    )
                    KeyboardContent.VOICE_EDITOR -> VoiceEditor()
                    KeyboardContent.TOPBAR_MENU -> TopBarEditMenu()
                }
            }

            // Nav Bar Padding
            Box(modifier = modifier.height(navBarPadding))
        }
        if (cursorActive) CursorOverlay(modifier.height(height.dp))
    }
}



@Composable
fun KeyboardKeyLayout(
    modifier: Modifier = Modifier,
    keyLayout : KeyLayout,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onPointerMove: (Offset) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        keyLayout.value.forEachIndexed { index, row ->
            key(index) {// use an index key so jetpack doesn't try to reused old Key info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Theme.colors.background)
                        .weight(1f),
                ) {
                    row.forEach { key ->
                        key(key) {
                            KeyButton(
                                key = key,
                                modifier = Modifier
                                    .weight(key.weight),
                                onKeyDown = onKeyDown,
                                onKeyUp = onKeyUp,
                                onPointerMove = onPointerMove,
                                shiftState = shiftState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CursorOverlay(
    modifier: Modifier = Modifier,
) {
    Box(// Cursor overlay
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0x99000000))
            .padding(4.dp),
        contentAlignment = Alignment.TopCenter
    )
    {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.ic_control),
                contentDescription = "",
                tint = Color(0xFFDDDDDD)
            )
            KeyText(
                text = "Drag to move cursor",
                color = Color(0xFFDDDDDD),
                size = 24.sp
            )
        }
    }
}


// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        KeyboardView(
            cursorActive = false
        )
    }
}
// endregion
