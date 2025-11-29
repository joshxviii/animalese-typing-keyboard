package com.example.animalese_typing

import android.content.Intent
import android.graphics.PixelFormat
import android.inputmethodservice.InputMethodService
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.animalese_typing.audio.AudioPlayer
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyPopout
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class ShiftState {
    OFF, ON, LOCKED
}
class AnimaleseIME : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator
    private lateinit var vibe : VibrationEffect
    private val _lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val _savedStateRegistryController = SavedStateRegistryController.create(this)
    private var repeatJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _pressedKey = MutableStateFlow<Key?>(null)
    private val pressedKey: StateFlow<Key?> = _pressedKey
    private val _shiftState = MutableStateFlow(ShiftState.OFF)
    val shiftState: StateFlow<ShiftState> = _shiftState
    private val _keyboardLayout = MutableStateFlow(KeyboardLayouts.QWERTY)
    val keyboardLayout: StateFlow<KeyboardLayouts> = _keyboardLayout

    var overlayView: ComposeView? = null
    lateinit var windowManager: WindowManager

    override fun onCreateInputView(): View {
        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AnimaleseTypingTheme {
                    val shiftStateValue by shiftState.collectAsStateWithLifecycle()
                    val keyboardLayoutValue by keyboardLayout.collectAsStateWithLifecycle()
                    val pressedKeyValue by pressedKey.collectAsStateWithLifecycle()
                    KeyboardView(
                        modifier = Modifier,
                        currentLayout = keyboardLayoutValue,
                        onSettings = ::handleSettings,
                        onKeyDown = ::onKeyDown,
                        onKeyUp = ::onKeyUp,
                        shiftState = shiftStateValue,
                        pressedKey = pressedKeyValue,
                        imeService = this@AnimaleseIME
                    )
                }
            }
            setOverlayContent {
                val pressedKeyValue by pressedKey.collectAsStateWithLifecycle()
                val shiftStateValue by shiftState.collectAsStateWithLifecycle()
                AnimaleseTypingTheme {
                    // TODO created a dedicated Overlay window for drawing Key menus etc
                    KeyPopout(
                        key = pressedKeyValue,
                        isUppercase = shiftStateValue != ShiftState.OFF
                    )
                }
            }
        }
        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this@AnimaleseIME)
            decorView.setViewTreeViewModelStoreOwner(this@AnimaleseIME)
            decorView.setViewTreeSavedStateRegistryOwner(this@AnimaleseIME)
        }
        return composeView
    }

    // region IME Overrides
    override val lifecycle: Lifecycle
        get() = _lifecycleRegistry

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore

    override val savedStateRegistry: SavedStateRegistry
        get() = _savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager


        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        vibrator = getSystemService(Vibrator::class.java)
        vibe = VibrationEffect.createWaveform(
            longArrayOf(0, 10),
            intArrayOf(0, 90),// TODO setting for vibration intensity
            -1
        )
        _savedStateRegistryController.performRestore(null)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
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
        repeatJob?.cancel()
    }

    fun setOverlayContent(content: @Composable (() -> Unit)) {
        val decorView = window?.window?.decorView ?: return
        overlayView?.let { windowManager.removeView(it) }

        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setViewTreeLifecycleOwner(this@AnimaleseIME)
            setViewTreeViewModelStoreOwner(this@AnimaleseIME)
            setViewTreeSavedStateRegistryOwner(this@AnimaleseIME)
            setContent { content() }
        }
        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            token = decorView.windowToken
        }

        overlayView = composeView
        windowManager.addView(composeView, params)
    }
    // endregion

    //region Event Handlers
    private fun onKeyDown(key: Key): Boolean {
        vibrator.vibrate(vibe)
        _pressedKey.value = key

        // handle repeating keys in coroutine
        if (key.isRepeatable) {
            repeatJob = coroutineScope.launch {
                handleKeyEvent(key.event)
                delay(500)
                while (true) {
                    handleKeyEvent(key.event)
                    delay(75)
                }
            }
        } else handleKeyEvent(key.event)
        return true
    }
    private fun onKeyUp(key: Key): Boolean {
        repeatJob?.cancel()
        if (_pressedKey.value == key) _pressedKey.value = null

        when (key) {
            is Key.CharKey -> {
                val isUppercase = _shiftState.value != ShiftState.OFF
                val charToCommit = if (isUppercase) key.char.uppercaseChar() else key.char.lowercaseChar()
                currentInputConnection?.commitText(charToCommit.toString(), 1)

                AudioPlayer.playSound( AudioPlayer.keycodeToSound( key.char.lowercaseChar().code ) )

                if (_shiftState.value == ShiftState.ON) _shiftState.value = ShiftState.OFF
            }
            else -> {}
        }
        return true
    }
    private fun handleKeyEvent(id: KeyFunctions) {
        if (id == KeyFunctions.NONE || id == KeyFunctions.CHARACTER) return
        AudioPlayer.playSound(AudioPlayer.keycodeToSound(0))
        when (id) {
            KeyFunctions.BACKSPACE -> {
                if (currentInputConnection?.getSelectedText(0) != null) {
                    currentInputConnection?.commitText("", 1)
                } else {
                    currentInputConnection?.deleteSurroundingText(1, 0)
                }
            }
            KeyFunctions.ENTER -> {
                currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            KeyFunctions.SHIFT -> {
                _shiftState.value = when (_shiftState.value) {
                    ShiftState.OFF -> ShiftState.ON
                    ShiftState.ON -> ShiftState.LOCKED
                    ShiftState.LOCKED -> ShiftState.OFF
                }
            }
            KeyFunctions.OPEN_NUMPAD -> _keyboardLayout.value = KeyboardLayouts.NUMPAD
            KeyFunctions.OPEN_KEYPAD -> _keyboardLayout.value = KeyboardLayouts.QWERTY // TODO: Get from settings
            KeyFunctions.OPEN_SPECIAL -> _keyboardLayout.value = KeyboardLayouts.SPECIAL
            KeyFunctions.OPEN_SPECIAL_ALT -> _keyboardLayout.value = KeyboardLayouts.SPECIAL_ALT
            else -> {}
        }
    }

    private fun handleSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    //endregion
}