package com.example.animalese_typing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

class SettingsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimaleseTypingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Keyboard Settings") }
                        )
                    }
                ) { innerPadding ->
                    SettingsScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {

}


// FOR PREVIEW ONLY
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    AnimaleseTypingTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Keyboard Settings") }
                )
            }
        ) { innerPadding ->
            SettingsScreen(Modifier.padding(innerPadding))
        }
    }
}