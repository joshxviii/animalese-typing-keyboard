package com.example.animalese_typing.ui.keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.animalese_typing.ShiftState
import com.example.animalese_typing.ui.keyboard.layouts.Azerty
import com.example.animalese_typing.ui.keyboard.layouts.Layout
import com.example.animalese_typing.ui.keyboard.layouts.Numpad
import com.example.animalese_typing.ui.keyboard.layouts.Qwerty
import com.example.animalese_typing.ui.keyboard.layouts.Qwertz
import com.example.animalese_typing.ui.keyboard.layouts.SpecialCharacters
import com.example.animalese_typing.ui.theme.AnimaleseColors

enum class KeyboardLayouts(val layout: Layout) {
    QWERTY(Qwerty),
    QWERTZ(Qwertz),
    AZERTY(Azerty),
    NUMPAD(Numpad),
    SPECIAL(SpecialCharacters)
}

@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    layout : Layout,
    onKeyDown: (Key) -> Unit = {},
    onKeyUp: (Key) -> Unit = {},
    shiftState: ShiftState = ShiftState.OFF
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AnimaleseColors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        layout.value.forEachIndexed { index, row ->
            key(index) {// use an index key so jetpack doesn't try to reused old Key info
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = AnimaleseColors.background)
                        .weight(1f),
                ) {
                    row.forEach { key ->
                        key(key) {
                            KeyButton(
                                key = key,
                                modifier = Modifier.weight(key.weight),
                                onKeyDown = onKeyDown,
                                onKeyUp = onKeyUp,
                                shiftState = shiftState
                            )
                        }
                    }
                }
            }
        }
    }
}