package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.opacity

/**
 * When [Key.CharKey.showPopup] is enabled [KeyPopout] will render
 * a preview above the key when pressed.
 */
@Composable
fun KeyPopout(
    key: Key,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(64.dp, 64.dp),
    offsetY: Dp = 70.dp,
) {
    val shape = RoundedCornerShape(50)

    val offset : IntOffset = with(LocalDensity.current) {
        IntOffset(
            ( key.coordinates.x.toInt() - (size.width/2).roundToPx() ),
            ( key.coordinates.y.toInt() - ((size.height/2) + offsetY).roundToPx()  )
        )
    }

    if (key is Key.CharKey && key.showPopup) Box(
        modifier = modifier
            .offset {offset}
            .size(size)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 4.dp,
                    offset = DpOffset(0.dp, 2.dp),
                    color = Color.Black.opacity(0.5f)
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(shape)
                .background(color = AnimaleseColors.keyBase)
                .padding(8.dp, 8.dp, 8.dp, 14.dp)
                .fillMaxSize()
        ) {
            KeyText(
                text = "${key.finalChar}",
                color = AnimaleseColors.keyText,
            )
        }
        if (key.subChars.isNotEmpty()) Icon(
            modifier = modifier
                .size(size/4)
                .align(Alignment.BottomCenter),
            imageVector = ImageVector.vectorResource(R.drawable.ic_elipsis),
            contentDescription = "",
            tint = AnimaleseColors.keyText
        )
    }
}

// region UI PREVIEW
@Preview(showBackground = false, widthDp = 96)
@Composable
fun KeyPopoutPreview() {
    AnimaleseTypingTheme {
        Column(
            modifier = Modifier.offset(
                x = 64.dp/2,
                y = (64.dp/2) + 70.dp
            )
        ) {
            KeyPopout(Key.CharKey('a'))
            KeyPopout(Key.CharKey('?', subChars = listOf('?', '!', '.')))
        }
    }
}
// endregion