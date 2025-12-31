package com.example.animalese_typing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
                            title = { Text("Animalese Keyboard") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    if(navController.previousBackStackEntry != null) {
                                        navController.popBackStack()
                                    }
                                    else finish()
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                                        contentDescription = "Back"
                                    )
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