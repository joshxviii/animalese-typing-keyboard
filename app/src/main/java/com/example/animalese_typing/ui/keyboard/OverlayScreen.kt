package com.example.animalese_typing.ui.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable

@Composable
fun ScreenOverlay(
    modifier: Modifier = Modifier,
    content: @Composable @UiComposable (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier,
//            .background(Color.Red.opacity(0.05f)),
        content = content
    )
}