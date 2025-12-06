package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.animalese_typing.AnimaleseIME
import com.example.animalese_typing.AnimalesePreferences
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayout
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.ui.theme.opacity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * The full keyboard view.
 */
@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    currentLayout: KeyboardLayouts = KeyboardLayouts.QWERTY,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    onPointerMove: (Offset) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onToggleResizeClick: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
    shiftState: AnimaleseIME.ShiftState = AnimaleseIME.ShiftState.OFF,
    cursorActive: Boolean = false,
    resizeActive: Boolean = false,
    showSuggestions: Boolean = false
) {
    val height by AnimalesePreferences(LocalContext.current).getKeyboardHeight().collectAsState(initial = 250f)
    val navBarPadding = max(WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(), 0.dp)

    Box() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.colors.background)
                .height(height.dp+navBarPadding)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Top Bar
            TopBar(
                onSettingsClick = onSettingsClick,
                onToggleResizeClick = onToggleResizeClick,
                onSuggestionClick = onSuggestionClick,
                showSuggestions = showSuggestions
            )

            // Keyboard
            KeyboardLayout(
                keyboardLayout = currentLayout.keyboardLayout,
                onKeyDown = onKeyDown,
                onKeyUp = onKeyUp,
                onPointerMove = onPointerMove,
                shiftState = shiftState,
                modifier = modifier
                    .padding(4.dp, 0.dp)
                    .weight(1f),
            )

            // Nav Bar Padding
            Box(modifier = modifier.height(navBarPadding))
        }
        if (cursorActive) CursorOverlay(modifier.height(height.dp))
    }
    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0,-900)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(Color.Red)
        )
    }
}



@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    keyboardLayout : KeyboardLayout,
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
        keyboardLayout.value.forEachIndexed { index, row ->
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
            modifier = modifier.padding(top=36.dp),
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
        KeyboardView()
    }
}
// endregion
