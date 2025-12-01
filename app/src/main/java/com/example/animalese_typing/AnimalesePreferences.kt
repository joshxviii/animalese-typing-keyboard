package com.example.animalese_typing

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.animalese_typing.ui.theme.AnimaleseThemes
import com.example.animalese_typing.ui.theme.ThemeColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AnimalesePreferences (val context: Context) {
    private val KEYBOARD_HEIGHT = floatPreferencesKey("keyboard_height")
    private val THEME = stringPreferencesKey("theme")
    private val SYSTEM_DEFAULT_THEME = booleanPreferencesKey("system_default")
    private val PLAY_SOUNDS = booleanPreferencesKey("play_sounds")
    private val VIBRATE = booleanPreferencesKey("vibrate")
    private val VIBRATION_INTENSITY = floatPreferencesKey("vibration_intensity")

    //
    suspend fun saveKeyboardHeight(height: Float) {context.dataStore.edit { it[KEYBOARD_HEIGHT] = height } }
    fun getKeyboardHeight(): Flow<Float> = context.dataStore.data.map { it[KEYBOARD_HEIGHT] ?: 250f }


    // TODO serialize themes as json for custom theme support
    suspend fun saveTheme(name: String) { context.dataStore.edit { it[THEME] = name } }
    fun getTheme(): Flow<ThemeColors> = context.dataStore.data.map { prefs -> AnimaleseThemes.fromName(prefs[THEME] ?: "LIGHT" ) }


    //
    suspend fun saveSystemDefaultTheme(isSystemDefault: Boolean) { context.dataStore.edit { it[SYSTEM_DEFAULT_THEME] = isSystemDefault } }
    fun getSystemDefaultTheme(): Flow<Boolean> = context.dataStore.data.map { it[SYSTEM_DEFAULT_THEME] ?: true }


    //
    suspend fun saveVibrationIntensity(intensity: Float) {context.dataStore.edit { it[VIBRATION_INTENSITY] = intensity } }
    fun getVibrationIntensity(): Flow<Float> = context.dataStore.data.map { it[VIBRATION_INTENSITY] ?: 0.3f }
}



