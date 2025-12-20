package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.utils.opacity

/**
 * When [Key.CharKey.showPopup] is enabled [KeyPopout] will render
 * a preview above the key when pressed.
 */
@Composable
fun KeyPopout(
    key: Key?,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(64.dp, 64.dp),
) {
    if (key == null) return

    val shape = RoundedCornerShape(50)

    Box {
        if (key is Key.CharKey && key.showPopup) Box(
            modifier = modifier
                .size(size)
                .dropShadow(
                    shape = shape,
                    shadow = Shadow(
                        radius = 4.dp,
                        offset = DpOffset(0.dp, 2.dp),
                        color = Color.Black.opacity(0.5f)
                    )
                )
                .clip(shape)
                .background(color = Theme.colors.keyBase),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp, 8.dp, 8.dp, 14.dp)
                    .fillMaxSize()
            ) {
                KeyText(
                    text = "${if (key.isUpperCase) key.char.uppercase() else key.char}",
                    color = Theme.colors.keyLabel,
                )
            }
            if (key.subChars.isNotEmpty()) Icon(
                modifier = Modifier
                    .size(size / 4)
                    .align(Alignment.BottomCenter),
                imageVector = ImageVector.vectorResource(R.drawable.ic_elipsis),
                contentDescription = "",
                tint = Theme.colors.keyLabel
            )
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 96)
@Composable
fun KeyPopoutPreview() {
    AnimaleseTypingTheme {
        Column {
            KeyPopout(Key.CharKey('a'))
            KeyPopout(Key.CharKey('?', subChars = listOf('?', '!', '.')))
        }
    }
}
// endregion