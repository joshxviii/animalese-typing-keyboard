package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Light


/**
 * If [Key.CharKey.subChars] is not empty [KeyPopoutMenu] will render
 * above the key when held down.
 */
@Composable
fun KeyPopoutMenu(
    key: Key,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    size: DpSize = DpSize(64.dp, 64.dp),
    offsetY: Dp = 70.dp,
) {
    val shape = RoundedCornerShape(33)

    val offset : IntOffset = with(LocalDensity.current) {
        IntOffset(
            ( key.coordinates.x.toInt() - (size.width/2).roundToPx() ),
            ( key.coordinates.y.toInt() - ((size.height/2) + offsetY).roundToPx()  )
        )
    }
    if ( key !is Key.CharKey || !key.showPopup || key.subChars.isEmpty()) return

    val maxColumns = 4
    val chars = key.subChars

    Box(
        modifier = modifier,
//            .offset {offset},
//            .dropShadow(
//                shape = shape,
//                shadow = Shadow(
//                    radius = 4.dp,
//                    offset = DpOffset(0.dp, 2.dp),
//                    color = Color.Black.opacity(0.5f)
//                )
//            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Layout(
            content = {
                chars.forEachIndexed {index, char ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .clip(shape)
                            .background(if (isSelected) AnimaleseColors.highlight else AnimaleseColors.keyBase),
                        contentAlignment = Alignment.Center
                    ) {
                        KeyText(
                            modifier = Modifier,
                            text = "$char",
                            color = if (isSelected) Color.White else AnimaleseColors.keyText,
                            size = 32.sp
                        )
                    }
                }
            },
            modifier = Modifier
                .background(AnimaleseColors.keyBase.copy(alpha = 0.95f))
                .padding(4.dp)
        ) { measurables, constraints ->
            val itemCount = measurables.size
            val columns = minOf(maxColumns, itemCount)
            val rows = (itemCount + columns - 1) / columns

            val itemWidth = constraints.maxWidth / columns
            val childConstraints = Constraints.fixedWidth(itemWidth)

            val placeables = measurables.map { it.measure(childConstraints) }

            val itemHeight = placeables.maxOf { it.height }
            val totalHeight = itemHeight * rows

            layout(constraints.maxWidth, totalHeight) {
                var index = 0
                for (row in (rows - 1) downTo 0) {
                    for (col in 0 until columns) {
                        if (index < itemCount) {
                            val placeable = placeables[index]
                            val x = col * itemWidth
                            val y = row * itemHeight
                            placeable.placeRelative(x, y)
                            index++
                        }
                    }
                }
            }
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = false, widthDp = 120)
@Composable
fun KeyPopoutMenuPreview() {
    AnimaleseTypingTheme(
        theme = Light
    ) {
        Column(
//            modifier = Modifier.offset(
//                x = 64.dp/2,
//                y = (64.dp/2) + 70.dp
//            )
        ) {
            KeyPopoutMenu(
                Key.CharKey(
                    char = 'a',
                    subChars = listOf('a', 'b', 'c', 'd', 'e', 'f')
                )
            )
        }
    }
}
// endregion