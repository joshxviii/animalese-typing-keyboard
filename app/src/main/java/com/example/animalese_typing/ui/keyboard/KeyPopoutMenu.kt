package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.ui.theme.opacity
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * If [Key.CharKey.subChars] is not empty [KeyPopoutMenu] will render
 * above the key when held down.
 */
@Composable
fun KeyPopoutMenu(
    key: Key?,
    modifier: Modifier = Modifier,
    itemSize: DpSize = DpSize(42.dp, 42.dp),
    pointerPosition: Offset = Offset.Unspecified,
    onSelectedIndexChange: (Int) -> Unit = {},
) {
    if (key == null) return
    val shape = RoundedCornerShape(45.dp)

    if ( key !is Key.CharKey || !key.showPopup || key.subChars.isEmpty()) return

    val itemPositions = remember { mutableStateMapOf<Int, Offset>() }
    var closestIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(pointerPosition) {
        // if finger is lifted or menu isn't ready, select the first item by default
        if (pointerPosition == Offset.Unspecified || itemPositions.isEmpty()) {
            onSelectedIndexChange(0)
            return@LaunchedEffect
        }

        var minDistance = Float.MAX_VALUE

        itemPositions.forEach { (index, itemCenter) ->
            val distance = sqrt(
                (pointerPosition.x - itemCenter.x).pow(2) + (pointerPosition.y - itemCenter.y).pow(2)
            )
            if (distance < minDistance) {
                minDistance = distance
                closestIndex = index
            }
        }
        onSelectedIndexChange(closestIndex)
    }

    Box {
        Layout(
            modifier = modifier
                .onGloballyPositioned {}
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
                key.subChars.forEachIndexed { index, char ->
                    val isSelected = index == closestIndex
                    Box(
                        modifier = Modifier
                            .size(itemSize)
                            .clip(shape)
                            .background(if (isSelected) Theme.colors.keyBaseHighlight else Color.Transparent)
                            .onGloballyPositioned { c->
                                val screenPos = c.positionOnScreen()
                                itemPositions[index] = Offset (
                                    screenPos.x + (c.size.width / 2f),
                                    screenPos.y + (c.size.height)
                                )
                            }
                    ) {
                        Box(
                            modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                            { KeyText(
                                modifier = Modifier,
                                text = "${if (key.isUpperCase) char.uppercaseChar() else char}",
                                color = if (isSelected) Color.White else Theme.colors.keyLabel,
                                size = 28.sp
                            )
                        }
                    }
                }
            }
            // position the characters in rows/columns
            //TODO: still needs some adjustments.
            // The first character in the list should be the closest to the touch point
        ) { measurables, constraints ->
            itemPositions.clear()

            val maxColumns = 5
            val horizontalGap = 6.dp.toPx()
            val verticalGap = 6.dp.toPx()

            val itemCount = measurables.size

            val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
            val itemWidth = placeables.maxOfOrNull { it.width } ?: 0
            val itemHeight = placeables.maxOfOrNull { it.height } ?: 0

            val columns = if (itemCount > maxColumns) ceil(itemCount / 2.0).toInt() else itemCount
            val rows = if (itemCount > maxColumns) 2 else 1

            val menuWidth = (itemWidth * columns + horizontalGap * (columns - 1)).toInt()
            val menuHeight = (itemHeight * rows + verticalGap * (rows - 1)).toInt()
            layout(menuWidth, menuHeight) {
                var currentItem = 0
                for (row in (rows - 1) downTo 0) {
                    val columnsInRow =
                        if (rows==1) columns
                        else ceil(itemCount / 2.0).toInt()

                    for (col in 0 until columnsInRow) {
                        if (currentItem < itemCount) {
                            val x = col * (itemWidth + horizontalGap)
                            val y = row * (itemHeight + verticalGap)
                            placeables[currentItem].placeRelative(x.toInt(), y.toInt())
                            currentItem++
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
                subChars = listOf('1','2','3','A','B'),
            )
        )
    }
}
// endregion