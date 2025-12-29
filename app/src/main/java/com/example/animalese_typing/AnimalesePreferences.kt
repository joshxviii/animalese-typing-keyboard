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
    companion object {
        val HEIGHT_RANGE = 200f..300f
        private val KEYBOARD_HEIGHT = floatPreferencesKey("keyboard_height")
        private val THEME = stringPreferencesKey("theme")
        private val SYSTEM_DEFAULT_THEME = booleanPreferencesKey("system_default")
        private val PLAY_SOUNDS = booleanPreferencesKey("play_sounds")
        private val VIBRATE = booleanPreferencesKey("vibrate")
        private val DEBUG_MODE = booleanPreferencesKey("debug_mode")
        private val VIBRATION_INTENSITY = floatPreferencesKey("vibration_intensity")
    }

    // KEYBOARD_HEIGHT
    suspend fun saveKeyboardHeight(height: Float) {
        context.dataStore.edit { it[KEYBOARD_HEIGHT] = height.coerceIn(HEIGHT_RANGE) }
    }
    fun getKeyboardHeight(): Flow<Float> = context.dataStore.data.map { it[KEYBOARD_HEIGHT] ?: 250f }


    // THEME
    // TODO serialize themes as json for custom theme support
    suspend fun saveTheme(name: String) { context.dataStore.edit { it[THEME] = name.lowercase() } }
    fun getTheme(): Flow<ThemeColors> = context.dataStore.data.map { prefs -> AnimaleseThemes.fromName(prefs[THEME] ?: "LIGHT" ) }


    // SYSTEM_DEFAULT_THEME
    suspend fun saveSystemDefaultTheme(isSystemDefault: Boolean) { context.dataStore.edit { it[SYSTEM_DEFAULT_THEME] = isSystemDefault } }
    fun getSystemDefaultTheme(): Flow<Boolean> = context.dataStore.data.map { it[SYSTEM_DEFAULT_THEME] ?: true }

    // PLAY_SOUNDS
    suspend fun savePlaySounds(playSounds: Boolean) { context.dataStore.edit { it[PLAY_SOUNDS] = playSounds } }
    fun getPlaySounds(): Flow<Boolean> = context.dataStore.data.map { it[PLAY_SOUNDS] ?: true }

    // VIBRATE
    suspend fun saveVibrate(vibrate: Boolean) { context.dataStore.edit { it[VIBRATE] = vibrate } }
    fun getVibrate(): Flow<Boolean> = context.dataStore.data.map { it[VIBRATE] ?: true }

    // DEBUG_MODE
    suspend fun saveDebugMode(debugMode: Boolean) { context.dataStore.edit { it[DEBUG_MODE] = debugMode } }
    fun getDebugMode(): Flow<Boolean> = context.dataStore.data.map { it[DEBUG_MODE] ?: false }

    // VIBRATION_INTENSITY
    suspend fun saveVibrationIntensity(intensity: Float) {context.dataStore.edit { it[VIBRATION_INTENSITY] = intensity } }
    fun getVibrationIntensity(): Flow<Float> = context.dataStore.data.map { it[VIBRATION_INTENSITY] ?: 0.3f }
}



