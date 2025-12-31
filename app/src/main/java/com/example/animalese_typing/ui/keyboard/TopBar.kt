package com.example.animalese_typing.ui.keyboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.AnimaleseIME.Companion.vibrate
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.TopBarButtons
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.utils.toIntOffset
import com.example.animalese_typing.utils.toOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableRow
import sh.calvin.reorderable.rememberReorderableLazyListState

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
 * The menu bar above the main keyboard. Displays various options and buttons.
 *
 * @param shownButtons List of buttons to be displayed.
 * @param showSuggestions Whether to show the suggestion bar.
 * @param showBackToKeyboardButton Whether to show the back to keyboard button.
 */
@Composable
fun TopBar(
    onSettingsClick: () -> Unit = {},
    onResizeClick: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
    onVoiceEditorClick: () -> Unit = {},
    onEmojiPickerClick: () -> Unit = {},
    onClipboardClick: () -> Unit = {},
    onBackToKeyboardClick: () -> Unit = {},
    onTopBarMenuClick: () -> Unit = {},
    showSuggestions: Boolean = false,
    showBackToKeyboardButton: Boolean = false,
) {
    val preferences = AnimalesePreferences(LocalContext.current)
    val shownButtonState = preferences.getTopBarButtons().collectAsState(initial = listOf(
        TopBarButtons.VOICE_EDITOR,
        TopBarButtons.EMOJI_PICKER,
        TopBarButtons.RESIZE,
        TopBarButtons.SETTINGS,
    ))
    var shownButtons by remember { mutableStateOf(shownButtonState.value) }

    Row(
        modifier = Modifier
            //.wrapContentSize(unbounded = true, align = Alignment.Center)
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
            val haptic = LocalHapticFeedback.current
            val lazyListState = rememberLazyListState()
            val reorderableLazyListState = rememberReorderableLazyListState(
                lazyListState,
                onMove = { from, to ->
                    shownButtons = shownButtons.toMutableList().apply {
                        add(to.index, removeAt(from.index))
                    }
                    // Save the new order
                    preferences.saveTopBarButtons(shownButtons)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )

            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .weight(1f)
            ) {
                items(
                    items = shownButtons,
                    key = { it }
                ) { button ->
                    ReorderableItem(
                        state = reorderableLazyListState,
                        key = button,
                    ) { isDragging ->
                        val dragColor = animateColorAsState(if (isDragging) Theme.colors.keyBaseHighlight else Color.Transparent)


                        TopBarButton(
                            icon = button.icon,
                            onClick = when (button) {
                                TopBarButtons.SETTINGS -> onSettingsClick
                                TopBarButtons.RESIZE -> onResizeClick
                                TopBarButtons.VOICE_EDITOR -> onVoiceEditorClick
                                TopBarButtons.EMOJI_PICKER -> onEmojiPickerClick
                                TopBarButtons.CLIPBOARD -> onClipboardClick
                            },
                            modifier = Modifier
                                .fillParentMaxWidth(1f/shownButtons.size)
                                .draggableHandle(
                                    onDragStarted = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    },
                                    onDragStopped = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                )
                                .clip(CircleShape)
                                .background(dragColor.value),
                            color = if (isDragging) Theme.colors.keyLabelHighlight else Theme.colors.iconButton
                        )
                    }
                }
            }
            if (shownButtons.isEmpty()) Spacer(modifier = Modifier.weight(1f).padding(16.dp, 0.dp))
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
    icon: Int,
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