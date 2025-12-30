package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme
import com.example.animalese_typing.utils.opacity

/**
 * Text prediction bar.
 * Displays a list of top suggestions.
 */
@Composable
fun TextSuggestBar(
    modifier: Modifier = Modifier,
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit = {},
    onExitSuggestion: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button. TODO: should set "showSuggestions" to false in parent
        TopBarButton(
            icon = R.drawable.ic_backout,
            onClick = onExitSuggestion,
            modifier = Modifier
        )

        // show top 3 suggestions only
        suggestions.take(3).forEachIndexed { i, suggestion ->
            if (i!=0) TextSeparator()
            Text(
                text = suggestion,
                color = Theme.colors.keyLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSuggestionClick("$suggestion ") }
            )
        }
    }
}

@Composable
fun TextSeparator(
    height: Int = 20,
    color: Color = Theme.colors.keyLabel.opacity(0.2f)
) {
    Canvas(modifier = Modifier
        .height(height.dp)
    ) {
        val strokeWidth = 1.dp.toPx()
        drawLine(
            color = color,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = strokeWidth,
        )
    }
}

// region UI PREVIEW
@Preview(showBackground = true, widthDp = 490)
@Composable
fun TextSuggestBarPreview() {
    AnimaleseTypingTheme {
        TextSuggestBar(suggestions = listOf("animalese", "keyboard", "test", "more"))
    }
}
// endregion