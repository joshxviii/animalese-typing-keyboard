package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText

/**
 * When [Key.CharKey.showPopup] is enabled [KeyPopout] will render
 * a preview above the key when pressed.
 */
@Composable
fun KeyPopout(
    key: Key?,
    modifier: Modifier = Modifier,
    isUppercase: Boolean = false,
) {
    if (key is Key.CharKey && key.showPopup) Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(50))
                .background(color = AnimaleseColors.highlight)
                .padding(8.dp, 8.dp)
        ) {
            KeyText(
                text = if (isUppercase) key.char.uppercase() else key.char.toString(),
                color = Color.White,

                )
        }
        if (key.subChars.isNotEmpty()) Icon(
            modifier = modifier.align(
                alignment = Alignment.BottomCenter
            ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_elipsis),
            contentDescription = "",
            tint = Color.White
        )
    }
    else {
        Box(
            modifier.fillMaxWidth()
        ) {
            Text(// TODO currently something needs to be composed to redraw the layer, even if the color is transparent
                color = Color.Transparent,
                text = ""
            )
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = false, widthDp = 96)
@Composable
fun KeyPopoutPreview() {
    AnimaleseTypingTheme {
        Column() {
            KeyPopout(Key.CharKey('a'))
            KeyPopout(Key.CharKey('?', subChars = listOf('?', '!', '.')))
        }
    }
}
// endregion