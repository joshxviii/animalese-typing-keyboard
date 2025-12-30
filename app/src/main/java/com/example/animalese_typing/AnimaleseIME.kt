package com.example.animalese_typing

import android.annotation.SuppressLint
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InlineSuggestionsResponse
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.window.Popup
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
import com.example.animalese_typing.ui.keyboard.KeyboardContent
import com.example.animalese_typing.ui.keyboard.KeyboardView
import com.example.animalese_typing.ui.keyboard.PopoutOverlay
import com.example.animalese_typing.ui.keyboard.ResizeOverlay
import com.example.animalese_typing.ui.keyboard.layouts.KeyLayouts
import com.example.animalese_typing.ui.theme.AnimaleseTypingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AnimaleseIME : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    companion object {
        lateinit var audioManager: AudioManager
        lateinit var vibrator: Vibrator
        lateinit var vibe : VibrationEffect
    }

    /**
     * The main IME window component.
     * renders the entire keyboard.
     */
    @SuppressLint("UnusedBoxWithConstraintsScope")
    @Composable
    fun IMEWindowContent() {
        AnimaleseTypingTheme {
            val shiftStateValue by shiftState.collectAsStateWithLifecycle()
            val showSuggestionsValue by showSuggestions.collectAsStateWithLifecycle()
            val cursorActiveValue by cursorActive.collectAsStateWithLifecycle()
            val resizeActiveValue by resizeActive.collectAsStateWithLifecycle()
            val pressedKeyValue by pressedKey.collectAsStateWithLifecycle()
            val popupMenuActiveValue by popupMenuActive.collectAsStateWithLifecycle()
            val pointerPositionValue by pointerPosition.collectAsStateWithLifecycle()

            val preferences = AnimalesePreferences(this)
            val debugMode by preferences.getDebugMode().collectAsState(initial = false)
            var keyboardHeight by remember {
                mutableStateOf(runBlocking {
                    preferences.getKeyboardHeight().first()
                })
            }

            KeyboardView(
                modifier = Modifier,
                height = keyboardHeight,
                currentContent = _keyboardContent.value,
                currentKeyLayout = _keyboardLayout.value,
                onContentChange = {
                    if (_keyboardContent.value == it) _keyboardContent.value = KeyboardContent.KEYBOARD
                    else _keyboardContent.value = it
                },
                onSettingsClick = ::handleSettings,
                onKeyDown = ::onKeyDown,
                onKeyUp = ::onKeyUp,
                onTextToCommitClick = ::onTextToCommitClick,
                onResizeClick = ::onResizeClick,
                onPointerMove = ::onPointerMove,
                shiftState = shiftStateValue,
                cursorActive = cursorActiveValue,
                showSuggestions = showSuggestionsValue
            )

            PopoutOverlay(
                key = pressedKeyValue,
                popupMenuActive = popupMenuActiveValue,
                pointerPosition = pointerPositionValue
            )

            if (resizeActiveValue) Popup() {
                ResizeOverlay(
                    initialHeight = keyboardHeight,
                    onResizeClick = ::onResizeClick,
                    onDragEnd = { newHeight ->
                        keyboardHeight = newHeight
                        coroutineScope.launch { preferences.saveKeyboardHeight(newHeight) }
                    }
                )
            }
        }
    }

    //region Event Handlers

    private fun onPointerMove(position: Offset) {
        val diff = position - _pointerPosition.value
        _pointerPosition.value = position

        if (_cursorActive.value) handleCursorMovement(diff)
    }

    private fun onKeyDown(key: Key): Boolean {
        vibrator.vibrate(vibe)
        _pressedKey.value = key
        logMessage("Key pressed: $key")

        handleKeyEvent(key.event)
        if (key.isRepeatable) { // repeating key logic
            repeatJob = coroutineScope.launch {
                delay(500)
                while (true) {
                    handleKeyEvent(key.event)
                    delay(50)
                }
            }
        }

        if (key is Key.CharKey && key.subChars.isNotEmpty()) {
            showPopupMenuJob = coroutineScope.launch { // show popup menu when holding key down
                delay(320)
                _popupMenuActive.value = true
            }
        }

        return true
    }
    private fun onKeyUp(key: Key): Boolean {// TODO: add compose typing
        when (key) {
            is Key.CharKey -> {
                currentInputConnection?.commitText(key.charToCommit, 1)
                AudioPlayer.playSound( AudioPlayer.keycodeToSound( key.charToCommit[0].lowercaseChar().code ) )
                if (_shiftState.value == ShiftState.ON) _shiftState.value = ShiftState.OFF
            }
            else -> {}
        }

        repeatJob?.cancel()
        showPopupMenuJob?.cancel()
        moveCursorJob?.cancel()
        _cursorActive.value = false
        _popupMenuActive.value = false
        _pointerPosition.value = Offset.Unspecified
        if (_pressedKey.value == key) _pressedKey.value = null
        return true
    }
    private fun onTextToCommitClick(textToCommit: String) {
        currentInputConnection?.commitText(textToCommit, 1)
    }
    private fun onResizeClick(value:Boolean? = null) {
        _resizeActive.value = value ?: !_resizeActive.value
    }
    private fun handleKeyEvent(id: KeyFunctions) {
        if (id == KeyFunctions.NONE || id == KeyFunctions.CHARACTER) return
        AudioPlayer.playSound(AudioPlayer.keycodeToSound(0))
        when (id) {
            KeyFunctions.SPACE -> {
                moveCursorJob = coroutineScope.launch {
                    delay(320)
                    _cursorActive.value = true
                }
                moveCursorJob?.invokeOnCompletion {// only commit SPACE if cursor was not activated
                    if (!_cursorActive.value) currentInputConnection?.commitText(" ", 1)
                }
            }
            KeyFunctions.BACKSPACE -> {
                if (currentInputConnection?.getSelectedText(0) != null) {
                    currentInputConnection?.commitText("", 1)
                } else {
                    currentInputConnection?.deleteSurroundingText(1, 0)
                }
            }
            KeyFunctions.ENTER -> sendIMEKeyEvent(KeyEvent.KEYCODE_ENTER)
            KeyFunctions.SHIFT -> {
                _shiftState.value = when (_shiftState.value) {
                    ShiftState.OFF -> ShiftState.ON
                    ShiftState.ON -> ShiftState.LOCKED
                    ShiftState.LOCKED -> ShiftState.OFF
                }
            }
            KeyFunctions.OPEN_NUMPAD -> _keyboardLayout.value = KeyLayouts.NUMPAD
            KeyFunctions.OPEN_KEYPAD -> _keyboardLayout.value = mainKeyboardLayout
            KeyFunctions.OPEN_SPECIAL -> _keyboardLayout.value = KeyLayouts.SPECIAL
            KeyFunctions.OPEN_SPECIAL_ALT -> _keyboardLayout.value = KeyLayouts.SPECIAL_ALT
            else -> {}
        }
    }

    private fun sendIMEKeyEvent(keyCode: Int) {
        currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        currentInputConnection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
    }

    private fun handleSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private val HORIZONTAL_MOVE_THRESHOLD = 20
    private val VERTICAL_MOVE_THRESHOLD = 60
    private var cursorH = 0
    private var cursorV = 0
    private fun handleCursorMovement(move: Offset) {
        cursorH += move.x.toInt()
        cursorV += move.y.toInt()

        while (cursorH > HORIZONTAL_MOVE_THRESHOLD) {// right
            sendIMEKeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT)
            cursorH -= HORIZONTAL_MOVE_THRESHOLD
        }

        while (cursorH < -HORIZONTAL_MOVE_THRESHOLD) {// left
            sendIMEKeyEvent(KeyEvent.KEYCODE_DPAD_LEFT)
            cursorH += HORIZONTAL_MOVE_THRESHOLD
        }

        while (cursorV > VERTICAL_MOVE_THRESHOLD) {// down
            sendIMEKeyEvent(KeyEvent.KEYCODE_DPAD_DOWN)
            cursorV -= VERTICAL_MOVE_THRESHOLD
        }

        while (cursorV < -VERTICAL_MOVE_THRESHOLD) {// up
            sendIMEKeyEvent(KeyEvent.KEYCODE_DPAD_UP)
            cursorV += VERTICAL_MOVE_THRESHOLD
        }
    }
    //endregion

    //region IME Variables

    enum class ShiftState {
        OFF, ON, LOCKED
    }
    private val _lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val _savedStateRegistryController = SavedStateRegistryController.create(this)
    private var repeatJob: Job? = null
    private var showPopupMenuJob: Job? = null
    private var moveCursorJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _cursorActive = MutableStateFlow<Boolean>(false)
    private val cursorActive: StateFlow<Boolean> = _cursorActive
    private val _resizeActive = MutableStateFlow<Boolean>(false)
    private val resizeActive: StateFlow<Boolean> = _resizeActive
    private val _popupMenuActive = MutableStateFlow<Boolean>(false)
    private val popupMenuActive: StateFlow<Boolean> = _popupMenuActive
    private val _pointerPosition = MutableStateFlow<Offset>(Offset.Unspecified)
    val pointerPosition: StateFlow<Offset> = _pointerPosition
    private val _pressedKey = MutableStateFlow<Key?>(null)
    private val pressedKey: StateFlow<Key?> = _pressedKey
    private val _shiftState = MutableStateFlow(ShiftState.OFF)
    val shiftState: StateFlow<ShiftState> = _shiftState
    private val _showSuggestions = MutableStateFlow<Boolean>(false)
    private val showSuggestions: StateFlow<Boolean> = _showSuggestions
    private val _keyboardContent = mutableStateOf(KeyboardContent.KEYBOARD)
    private val _keyboardLayout = MutableStateFlow(KeyLayouts.QWERTY)
    private var mainKeyboardLayout = KeyLayouts.QWERTY // TODO: get from settings
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

    override fun onCreate() {
        super.onCreate()
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

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(editorInfo, restarting)
        _keyboardContent.value = KeyboardContent.KEYBOARD
        _keyboardLayout.value = mainKeyboardLayout
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
        _keyboardLayout.value = mainKeyboardLayout
        _resizeActive.value = false
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