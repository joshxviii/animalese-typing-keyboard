package com.example.animalese_typing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animalese_typing.ui.settings.GeneralSettingsScreen
import com.example.animalese_typing.ui.settings.LayoutSettingsScreen
import com.example.animalese_typing.ui.settings.MainSettingsScreen
import com.example.animalese_typing.ui.settings.ThemesSettingsScreen
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

class SettingsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimaleseTypingTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Keyboard Settings") },
                            navigationIcon = {
                                if (navController.previousBackStackEntry != null) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main_settings",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main_settings") { MainSettingsScreen(navController) }
                        composable("themes") { ThemesSettingsScreen() }
                        composable("general") { GeneralSettingsScreen() }
                        composable("layout") { LayoutSettingsScreen() }
                    }
                }
            }
        }
    }
}