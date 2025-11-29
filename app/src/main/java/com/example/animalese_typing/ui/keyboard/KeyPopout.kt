package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText

val size = DpSize(50.dp, 50.dp)
val offsetY = 40.dp

/**
 * When [Key.CharKey.showPopup] is enabled [KeyPopout] will render
 * a preview above the key when pressed.
 */
@Composable
fun KeyPopout(
    key: Key,
    modifier: Modifier = Modifier,
    isUppercase: Boolean = false,
) {
    //val size = DpSize(key.size.width.dp,key.size.height.dp)

    val offset : IntOffset = with(LocalDensity.current) {
        IntOffset(
            ( key.coordinates.x.toInt() - (size.width/2).roundToPx() ),
            ( key.coordinates.y.toInt() - ((size.height/2) + offsetY).roundToPx()  )
        )
    }

    if (key is Key.CharKey && key.showPopup) Box(
        modifier = modifier
            .offset {offset}
            .size(size),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(50))
                .background(color = AnimaleseColors.highlight)
                .padding(8.dp, 8.dp)
                .fillMaxSize()
        ) {
            KeyText(
                text = "${key.finalChar}",
                color = Color.White,
            )
        }
        if (key.subChars.isNotEmpty()) Icon(
            modifier = modifier
                .size(size/4)
                .align(Alignment.BottomCenter),
            imageVector = ImageVector.vectorResource(R.drawable.ic_elipsis),
            contentDescription = "",
            tint = Color.White
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
                x = size.width/2,
                y = (size.height/2) + offsetY
            )
        ) {
            KeyPopout(Key.CharKey('a'))
            KeyPopout(Key.CharKey('?', subChars = listOf('?', '!', '.')))
        }
    }
}
// endregion