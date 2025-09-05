package com.example.animalese_typing


import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_ASSISTANCE_SONIFICATION
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast

@Suppress("DEPRECATION")
class IMEService: InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private val TAG = "AnimaleseIME"
    private lateinit var audioManager: AudioManager

    /**
     * Flag to track whether preview is enabled or not
      */
    private var wasPreviewEnabled: Boolean = true

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        AppContext.initialize(this)
        VoiceProfileManager.initialize(this)
    }

    override fun onCreateInputView(): View {

        val entireInputView = layoutInflater.inflate(R.layout.keyboard_layout, null) // Use your actual layout file name

        keyboardView = entireInputView.findViewById<KeyboardView>(R.id.keyboard) // Use the ID you defined in XML

        keyboard = Keyboard(this, R.xml.keys_qwerty)
        keyboardView.setKeyboard(keyboard)
        keyboardView.setOnKeyboardActionListener(this)
        keyboardView.isPreviewEnabled = false // TODO enable preview only for letter/numbers/punctuation keys

        val settingsButton = entireInputView.findViewById<ImageButton>(R.id.settings_button)
        val keyboardSizeButton = entireInputView.findViewById<ImageButton>(R.id.size_button)
        settingsButton.setOnClickListener {
            Log.d(TAG, "Settings Button Clicked")
            val intent = Intent(this, SettingsActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            try { startActivity(intent) }
            catch (e: Exception) {
                Log.e(TAG, "Error Starting SettingsActivity", e)
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        keyboardSizeButton.setOnClickListener {
            // TODO
        }

        return entireInputView
    }


    @Deprecated("")
    override fun onPress(primaryCode: Int) {
        Log.d(TAG, "onPress called with primaryCode: $primaryCode")

        val char = primaryCode.toChar()
        if (Character.isLetterOrDigit(char) || primaryCode == 32 /* Space */) { // Example condition
            val key = when (primaryCode) {
                32 -> "space"
                Keyboard.KEYCODE_DELETE -> "delete"
                Keyboard.KEYCODE_DONE -> "enter"
                // Add more special key mappings
                else -> primaryCode.toChar().toString().lowercase()
            }
            val audioPath = VoiceProfileManager.getSoundFromKey(key)
            AudioPlayer.playSound( audioPath )
        } else {
            // Generic key press sound
            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
        }
//        if (shouldDisablePreviewForCode(primaryCode)) {
//            if (keyboardView.isPreviewEnabled) {
//                wasPreviewEnabled = true
//                keyboardView.isPreviewEnabled = false
//            } else {
//                wasPreviewEnabled = false
//            }
//        } else {
//            if (!keyboardView.isPreviewEnabled && wasPreviewEnabled) {
//                keyboardView.isPreviewEnabled = true
//            }
//        }
    }

    @Deprecated("")
    override fun onRelease(primaryCode: Int) {
        Log.d(TAG, "onRelease called with primaryCode: $primaryCode")
//        if (!keyboardView.isPreviewEnabled && wasPreviewEnabled) {
//            keyboardView.isPreviewEnabled = true
//        }
    }

    private fun shouldDisablePreviewForCode(primaryCode: Int): Boolean {
        return when (primaryCode) {
            32,                           // Space
            Keyboard.KEYCODE_SHIFT,       // -1 (Shift)
            Keyboard.KEYCODE_DELETE,      // -5 (Delete)
            Keyboard.KEYCODE_DONE,        // -4 (Enter/Done)
            10,                               // Newline (alternative for Enter)
            Keyboard.KEYCODE_MODE_CHANGE, // -2 (Mode change, e.g., to symbols/numbers)
            Keyboard.KEYCODE_CANCEL,       // -3 (Cancel)
                -> true
            else -> false
        }
    }

    /**
     * Called when a key is pressed on the keyboard.
     * Commits the text to input fields.
     */
    @Deprecated("")
    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        inputConnection?.let { ic ->
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
                Keyboard.KEYCODE_DONE -> sendKeyChar('\n')
                else -> {
                    val code = primaryCode.toChar()
                    if (Character.isDefined(code)) {
                        ic.commitText(code.toString(), 1)
                    }
                }
            }
        }
    }

    @Deprecated("")
    override fun onText(text: CharSequence?) {}

    @Deprecated("")
    override fun swipeDown() {}

    @Deprecated("")
    override fun swipeLeft() {}

    @Deprecated("")
    override fun swipeRight() {}

    @Deprecated("")
    override fun swipeUp() {}
}