package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.R
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Background
import com.example.animalese_typing.ui.theme.KeyIcon

@Composable
fun TopBar(
    onSettings: () -> Unit = {},
    onResize: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
            .background(color = Background),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TopBarButton(
            icon = R.drawable.ic_settings,
            onClick = onSettings,
            modifier = Modifier.weight(1f)
        )
        TopBarButton(
            icon = R.drawable.ic_clipboard,
            onClick = {},
            modifier = Modifier.weight(1f)
        )
        TopBarButton(
            icon = R.drawable.ic_keyboard_resize,
            onClick = onResize,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TopBarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Int
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(24.dp)
    ) {
        Icon(painter = painterResource(icon), contentDescription = "", tint = KeyIcon)
    }
}

// ONLY USED FOR PREVIEWING
@Preview(showBackground = true, widthDp = 490)
@Composable
fun TopBarPreview() {
    AnimaleseTypingTheme {
        TopBar()
    }
}