package com.example.animalese_typing


import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.animalese_typing.AnimaleseTyping.Companion.logMessage

@Suppress("DEPRECATION")
class IMEService: InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private var inputView: View? = null
    private lateinit var audioManager: AudioManager
    private var wasPreviewEnabled: Boolean = true

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
    }

    override fun onCreateInputView(): View {

        val entireInputView = layoutInflater.inflate(R.layout.keyboard_layout, null) // Use your actual layout file name
        this.inputView = entireInputView
        keyboardView = entireInputView.findViewById<KeyboardView>(R.id.keyboard) // Use the ID you defined in XML

        keyboard = Keyboard(this, R.xml.keys_qwerty)
        keyboardView.setKeyboard(keyboard)
        keyboardView.setOnKeyboardActionListener(this)
        keyboardView.isPreviewEnabled = false // TODO enable preview only for letter/numbers/punctuation keys

        val settingsButton = entireInputView.findViewById<ImageButton>(R.id.settings_button)
        val keyboardSizeButton = entireInputView.findViewById<ImageButton>(R.id.size_button)
        settingsButton.setOnClickListener {
            logMessage("Settings Button Clicked")
            val intent = Intent(this, SettingsActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            try { startActivity(intent) }
            catch (e: Exception) {
                logMessage("Error Starting SettingsActivity $e")
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        keyboardSizeButton.setOnClickListener {
            // TODO
        }

        // Dynamic padding for keyboard so it doesn't block nav bar
        ViewCompat.setOnApplyWindowInsetsListener(entireInputView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            view.updatePadding(bottom = systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        return entireInputView
    }

    override fun onWindowShown() {
        super.onWindowShown()
        inputView?.requestApplyInsets()
    }

    @Deprecated("")
    override fun onPress(primaryCode: Int) {
        logMessage("onPress called with primaryCode: $primaryCode")

        val soundPath = AudioPlayer.keycodeToSound( primaryCode )
        AudioPlayer.playSound( soundPath )

    }

    @Deprecated("")
    override fun onRelease(primaryCode: Int) {
        logMessage("onRelease called with primaryCode: $primaryCode")
//        if (!keyboardView.isPreviewEnabled && wasPreviewEnabled) {
//            keyboardView.isPreviewEnabled = true
//        }
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