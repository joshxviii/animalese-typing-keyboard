package com.example.animalese_typing.ui.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

@Composable
fun MainSettingsScreen(navController: NavController? = null) {
    SettingsList {

        SettingsCategory(title = "Settings")
        SettingsItem(
            title = "General Settings",
            onClick = { navController?.navigate("general") }
        )
        SettingsItem(
            title = "Themes",
            onClick = { navController?.navigate("themes") }
        )
        SettingsItem(
            title = "Keyboard Layout",
            onClick = { navController?.navigate("layout") }
        )

        SettingsCategory(title = "About")
        SettingsItem(title = "Version", subtitle = "")
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingsScreenPreview() {
    AnimaleseTypingTheme {
        MainSettingsScreen()
    }
}

@Composable
fun SettingsList(content: @Composable ColumnScope.() -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Column(
                content = content
            )
        }
    }
}

@Composable
fun SettingsCategory(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    enabled: Boolean = true,
    control: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { if (subtitle != null) Text(subtitle) },
        trailingContent = control,
        modifier = Modifier
            .clickable(enabled = enabled, onClick = onClick)
    )
}

@Composable
fun RadioButtonItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = {
            RadioButton(selected = selected, onClick = onClick)
        },
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun SliderItem(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) {
    Column {
        Text(title)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}