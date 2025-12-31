package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.KeyText

@Composable
fun CursorOverlay(
    modifier: Modifier = Modifier,
) {
    Box(// Cursor overlay
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0x99000000))
            .padding(4.dp),
        contentAlignment = Alignment.TopCenter
    )
    {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.ic_control),
                contentDescription = "",
                tint = Color(0xFFDDDDDD)
            )
            KeyText(
                text = "Drag to move cursor",
                color = Color(0xFFDDDDDD),
                size = 24.sp
            )
        }
    }
}

// region UI PREVIEW
@Preview(showBackground = true)
@Composable
fun CursorOverlayPreview() {
    AnimaleseTypingTheme(
        theme = AnimaleseThemes.Light
    ) {
        AnimaleseKeyboard(
            cursorActive = true
        )
    }
}
// endregion