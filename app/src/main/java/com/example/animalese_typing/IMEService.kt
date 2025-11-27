package com.example.animalese_typing

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.View
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyboardScreen
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme

class IMEService : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator
    private lateinit var vibe : VibrationEffect
    private val _lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val _savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle
        get() = _lifecycleRegistry

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore

    override val savedStateRegistry: SavedStateRegistry
        get() = _savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        vibrator = getSystemService(Vibrator::class.java)
        vibe = VibrationEffect.createWaveform(
        longArrayOf(0, 10),
        intArrayOf(0, 125),// TODO setting for vibration intensity
        -1
        )
        _savedStateRegistryController.performRestore(null)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onCreateInputView(): View {
        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AnimaleseTypingTheme {
                    KeyboardScreen(
                        modifier = Modifier,
                        onSettings = ::handleSettings,
                        onKeyDown = ::onKeyDown,
                        onKeyUp = ::onKeyUp
                    )
                }
            }
        }

        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this@IMEService)
            decorView.setViewTreeViewModelStoreOwner(this@IMEService)
            decorView.setViewTreeSavedStateRegistryOwner(this@IMEService)
        }

        return composeView
    }

    override fun onWindowShown() {
        super.onWindowShown()
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onWindowHidden() {
        super.onWindowHidden()
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
    }

    override fun onDestroy() {
        super.onDestroy()
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        _viewModelStore.clear()
    }

    //region Event Handlers

    // TODO handle key stuff
    private fun onKeyDown(key: Key) {
        AnimaleseTyping.logMessage("KEY DOWN: $key")
        vibrator.vibrate(vibe)
        if (key.function != null) handleFunction(key.function)
    }
    private fun onKeyUp(key: Key) {
        AnimaleseTyping.logMessage("KEY UP: $key")
        when (key) {
            is Key.CharKey -> handleChar(key.char)
            else -> {}
        }
    }

    private fun handleSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun handleChar(char: Char) {
        currentInputConnection?.commitText(char.toString(), 1)

        //TODO Improve playback performance.
        // Same old audio play logic as before
        AudioPlayer.playSound( AudioPlayer.keycodeToSound( char.lowercaseChar().code ) )
    }

    private fun handleFunction(id: KeyFunctionIds) {
        when (id) {
            KeyFunctionIds.BACKSPACE -> {
                currentInputConnection?.deleteSurroundingText(1, 0)
            }
            KeyFunctionIds.ENTER -> {
                currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            KeyFunctionIds.SHIFT -> {
                //TODO on/off/lock
            }
            KeyFunctionIds.OPEN_NUMPAD -> {}
            KeyFunctionIds.OPEN_KEYPAD -> {}
            KeyFunctionIds.OPEN_SPECIAL -> {}
            else -> {}
        }
    }
    //endregion
}