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
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InlineSuggestionsResponse
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.WindowCompat
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
import com.example.animalese_typing.AnimaleseTyping.Companion.logMessage
import com.example.animalese_typing.audio.AudioPlayer
import com.example.animalese_typing.ui.keyboard.Key
import com.example.animalese_typing.ui.keyboard.KeyFunctions
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.keyboard.PopoutOverlay
import com.example.animalese_typing.ui.keyboard.layouts.KeyboardLayouts
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimaleseIME : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    /**
     * The main IME window component.
     * renders the entire keyboard.
     */
    @Composable
    fun IMEWindowContent() {
        AnimaleseTypingTheme {
            val shiftStateValue by shiftState.collectAsStateWithLifecycle()
            val keyboardLayoutValue by keyboardLayout.collectAsStateWithLifecycle()
            val showSuggestionsValue by showSuggestions.collectAsStateWithLifecycle()
            KeyboardView(
                modifier = Modifier,
                currentLayout = keyboardLayoutValue,
                onSettings = ::handleSettings,
                onKeyDown = ::onKeyDown,
                onKeyUp = ::onKeyUp,
                onSuggestionClick = ::onSuggestionClick,
                onPointerMove = ::onPointerMove,
                shiftState = shiftStateValue,
                showSuggestions = showSuggestionsValue
            )
        }
    }

    /**
     * The overlay window for popup menus etc.
     */
    @Composable
    fun OverlayWindowContent() {
        val pressedKeyValue by pressedKey.collectAsStateWithLifecycle()
        val showPopupMenuValue by showPopupMenu.collectAsStateWithLifecycle()
        val pointerPositionValue by pointerPosition.collectAsStateWithLifecycle()
        val selectedMenuIndexValue by selectedMenuIndex.collectAsStateWithLifecycle()
        //TODO:
        // The overlay consumes user touches on some devices. setting the visibility off disables touch completely
        // but setting the overlay visibility is relatively slow.
        // This would not be necessary if the pass through touch was working with One UI systems
        // have to look into this...
        overlayView?.visibility = if (pressedKeyValue != null) View.VISIBLE else View.GONE
        AnimaleseTypingTheme {
            PopoutOverlay(
                modifier = Modifier.fillMaxSize(),
                key = pressedKeyValue,
                showMenu = showPopupMenuValue,
                pointerPosition = pointerPositionValue,
                onSelectedIndexChange = { newIndex ->
                    _selectedMenuIndex.value = newIndex
                }
            )
        }
    }

    //region Event Handlers
    private fun onPointerMove(position: Offset) {
        _pointerPosition.value = position
    }

    private fun onKeyDown(key: Key): Boolean {
        vibrator.vibrate(vibe)
        _pressedKey.value = key
        _showPopupMenu.value = false
        logMessage("Key pressed: $key")

        if (key.isRepeatable) { // repeating key logic
            repeatJob = coroutineScope.launch {
                handleKeyEvent(key.event)
                delay(500)
                while (true) {
                    handleKeyEvent(key.event)
                    delay(50)
                }
            }
        } else handleKeyEvent(key.event)

        showPopupJob = coroutineScope.launch { // show popup menu when holding key down
            delay(400)
            _showPopupMenu.value = (key is Key.CharKey && key.subChars.isNotEmpty())
        }

        return true
    }
    private fun onKeyUp(key: Key): Boolean {// TODO: add compose typing
        when (key) {
            is Key.CharKey -> {
                val charToCommit = // if the alternate key menu is shown get the selected character, otherwise get the key's default char. then apply casing.
                    (if (_showPopupMenu.value) key.subChars[_selectedMenuIndex.value] else key.char)
                        .let { if (key.isUpperCase) it.uppercase() else it.lowercase() }

                currentInputConnection?.commitText(charToCommit, 1)
                AudioPlayer.playSound( AudioPlayer.keycodeToSound( charToCommit[0].lowercaseChar().code ) )
                if (_shiftState.value == ShiftState.ON) _shiftState.value = ShiftState.OFF
            }
            else -> {}
        }

        repeatJob?.cancel()
        showPopupJob?.cancel()
        _showPopupMenu.value = false
        _pointerPosition.value = Offset.Unspecified
        if (_pressedKey.value == key) _pressedKey.value = null
        return true
    }
    private fun onSuggestionClick(suggestion: String) {
        currentInputConnection?.commitText("$suggestion ", 1)
    }
    private fun handleKeyEvent(id: KeyFunctions) {
        if (id == KeyFunctions.NONE || id == KeyFunctions.CHARACTER) return
        AudioPlayer.playSound(AudioPlayer.keycodeToSound(0))
        when (id) {
            KeyFunctions.SPACE -> {
                currentInputConnection?.commitText(" ", 1)
            }
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
            //TODO: currently switching layouts is kinda slow, maybe preload then first then hide/show
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

    //region IME Variables

    enum class ShiftState {
        OFF, ON, LOCKED
    }
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator
    private lateinit var vibe : VibrationEffect
    private val _lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val _savedStateRegistryController = SavedStateRegistryController.create(this)
    private var repeatJob: Job? = null
    private var showPopupJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _pressedKey = MutableStateFlow<Key?>(null)
    private val pressedKey: StateFlow<Key?> = _pressedKey
    private val _showPopupMenu = MutableStateFlow<Boolean>(false)
    private val showPopupMenu: StateFlow<Boolean> = _showPopupMenu
    private val _selectedMenuIndex = MutableStateFlow<Int>(0)
    private val selectedMenuIndex: StateFlow<Int> = _selectedMenuIndex
    private val _pointerPosition = MutableStateFlow<Offset>(Offset.Unspecified)
    val pointerPosition: StateFlow<Offset> = _pointerPosition
    private val _shiftState = MutableStateFlow(ShiftState.OFF)
    val shiftState: StateFlow<ShiftState> = _shiftState
    private val _showSuggestions = MutableStateFlow<Boolean>(false)
    private val showSuggestions: StateFlow<Boolean> = _showSuggestions
    private val _keyboardLayout = MutableStateFlow(KeyboardLayouts.QWERTY)
    val keyboardLayout: StateFlow<KeyboardLayouts> = _keyboardLayout
    var overlayView: ComposeView? = null
    lateinit var windowManager: WindowManager
    // endregion

    // region IME Overrides
    override val lifecycle: Lifecycle
        get() = _lifecycleRegistry

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore

    override val savedStateRegistry: SavedStateRegistry
        get() = _savedStateRegistryController.savedStateRegistry

    override fun onCreateInputView(): View {
        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { IMEWindowContent() }
        }
        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this@AnimaleseIME)
            decorView.setViewTreeViewModelStoreOwner(this@AnimaleseIME)
            decorView.setViewTreeSavedStateRegistryOwner(this@AnimaleseIME)
        }
        return composeView
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        window?.window?.decorView?.post {
            setOverlayContent { OverlayWindowContent() }
        }
    }

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
        window.window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_SPLIT_TOUCH or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            token = decorView.windowToken
        }

        overlayView = composeView
        try {
            windowManager.addView(composeView, params)
        } catch (e: Exception) { e.printStackTrace() }
    }
    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
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
    override fun onDestroy() {
        super.onDestroy()
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        _viewModelStore.clear()
        repeatJob?.cancel()
    }

    override fun onInlineSuggestionsResponse(response: InlineSuggestionsResponse): Boolean {
        return super.onInlineSuggestionsResponse(response)
    }
    // endregion
}