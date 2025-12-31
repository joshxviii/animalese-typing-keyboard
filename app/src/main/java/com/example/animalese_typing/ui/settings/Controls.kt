package com.example.animalese_typing.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import com.example.animalese_typing.ui.theme.Theme

@Composable
fun SettingsList(content: @Composable ColumnScope.() -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(0.dp),
                content = content,
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
        headlineContent = { Text(
            color = Theme.colors.defaultText,
            text = title
        ) },
        supportingContent = { if (subtitle != null) Text(subtitle) },
        trailingContent = control,
        modifier = Modifier
            .clickable(enabled = enabled, onClick = onClick)
    )
}

@Composable
fun SelectorItem(
    title: String,
    options: List<String>,
    onClick: () -> Unit = {},
    selected: Int = 0,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    Text(
        color = Theme.colors.defaultText,
        text = title,
        modifier = Modifier.clickable(
            enabled = enabled,
            onClick = { expanded = true }
        )
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {}
    ) {
        for (i in options.indices) {
            DropdownMenuItem(
                text = { Text(options[i]) },
                onClick = {
                    expanded = false
                    onClick()
                }
            )
        }
    }
}

@Composable
fun RadioButtonItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    ListItem(
        headlineContent = {
            Text(
                color = Theme.colors.defaultText,
                text = title
            )
        },
        trailingContent = {
            RadioButton(
                selected = selected,
                onClick = onClick,
                enabled = enabled
            )
        },
        modifier = Modifier.clickable(enabled = enabled) { onClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderItem(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) {
    Column(
        modifier = Modifier
            .height(42.dp),
    ) {
        Text(
            color = Theme.colors.defaultText,
            text = title
        )

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.size(16.dp)
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    modifier = Modifier
                        .height(8.dp)
                )
            }
        )
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ControlsPreview() {
    AnimaleseTypingTheme {
        SettingsList {
            SettingsCategory("Test")
            SettingsItem(
                title = "Test",
                subtitle = "Description Text",
            )
            SettingsItem(
                title = "Test",
                subtitle = "Description Text",
            )

            SliderItem(
                title = "Slider",
                value = 0.5f,
                onValueChange = {},
            )

            SettingsItem(
                title = "Switch",
                control = {
                    Switch(checked = true, onCheckedChange = {})
                },
            )

            RadioButtonItem(title = "Radio Button", selected = true, onClick = {})
            RadioButtonItem(title = "Radio Button", selected = true, onClick = {})
            RadioButtonItem(title = "Radio Button", selected = true, onClick = {})

            SelectorItem(
                title = "Selector",
                options = listOf(
                    "Option 1",
                    "Option 2",
                    "Option 3",
                )
            )
        }
    }
}