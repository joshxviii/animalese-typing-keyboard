package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.ui.theme.opacity


/**
 * If [Key.CharKey.subChars] is not empty [KeyPopoutMenu] will render
 * above the key when held down.
 */
@Composable
fun KeyPopoutMenu(
    key: Key?,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    size: DpSize = DpSize(40.dp, 46.dp),
    touchOffset: IntOffset = IntOffset(1,1),
    itemPositions: SnapshotStateMap<Int, Offset> = SnapshotStateMap(),
) {
    if (key == null) return
    val shape = RoundedCornerShape(45.dp)

    if ( key !is Key.CharKey || !key.showPopup || key.subChars.isEmpty()) return

    val maxColumns = 4
    val chars = key.subChars

    Box {
        Layout(
            modifier = modifier
                .dropShadow(
                    shape = shape,
                    shadow = Shadow(
                        radius = 4.dp,
                        offset = DpOffset(0.dp, 2.dp),
                        color = Color.Black.opacity(0.5f)
                    )
                )
                .clip(shape)
                .background(Theme.colors.keyBase)
                .padding(12.dp),
            content = {
                chars.forEachIndexed { index, char ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(shape)
                            .background(if (isSelected) Theme.colors.highlight else Color.Transparent),
                    ) {
                        Box(
                            modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                            { KeyText(
                                modifier = Modifier,
                                text = "${if (key.isUpperCase) char.uppercaseChar() else char}",
                                color = if (isSelected) Color.White else Theme.colors.keyText,
                                size = 32.sp
                            )
                        }
                    }
                }
            }
            // position the alternate characters in rows/columns
            // and save relative location for drag inputs
        ) { measurables, constraints ->
            itemPositions.clear()

            val itemCount = measurables.size
            val columns = minOf(maxColumns, itemCount)
            val rows = (itemCount + columns - 1) / columns

            val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }

            val itemWidth = placeables.maxOf { it.width }
            val itemHeight = placeables.maxOf { it.height }

            layout(itemWidth * columns, itemHeight * rows) {
                var index = 0
                for (row in (rows - 1) downTo 0) {
                    for (col in 0 until columns) {
                        if (index < itemCount) {
                            val placeable = placeables[index]
                            val x = col * itemWidth
                            val y = row * itemHeight
                            placeable.placeRelative(x, y)

                            val itemCenterOnScreen = Offset(
                                x = x + (itemWidth / 2f) - touchOffset.x,
                                y = y + (itemHeight / 2f) - touchOffset.y
                            )
                            itemPositions[index] = itemCenterOnScreen

                            index++
                        }
                    }
                }
            }
        }
    }

}

// region UI PREVIEW
@Preview(showBackground = false)
@Composable
fun KeyPopoutMenuPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Dark
    ) {
        KeyPopoutMenu(
            Key.CharKey(
                char = 'a',
                subChars = listOf('a', 'b', 'c', 'd', 'e', 'f'),
            )
        )
    }
}
// endregion