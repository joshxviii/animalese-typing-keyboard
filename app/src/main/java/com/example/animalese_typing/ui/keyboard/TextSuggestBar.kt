package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseColors
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun TextSuggestBar(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit = {},
    onExitSuggestion: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Back button
        IconButton(
            onClick = onExitSuggestion,
            modifier = Modifier.size(
                24.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_backout),
                contentDescription = "",
                tint = AnimaleseColors.keyText
            )
        }

        // show top 3 suggestions
        suggestions.slice(0..2)
            .forEach { suggestion ->
            Text(
                text = suggestion,
                color = AnimaleseColors.keyText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSuggestionClick(suggestion) }
            )
        }
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