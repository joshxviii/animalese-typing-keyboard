package com.example.animalese_typing


import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.Log
import android.view.View

@Suppress("DEPRECATION")
class IMEService: InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private val TAG = "AnimaleseIME"

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_layout, null) as KeyboardView
        keyboard = Keyboard(this, R.xml.keyboard_keys)  // Define this XML in Step 7
        keyboardView.setKeyboard(keyboard)
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        // Log the primaryCode
        Log.d(TAG, "onKey called with primaryCode: $primaryCode (char: ${primaryCode.toChar()})")

        // Log keyCodes array if not null
        if (keyCodes != null) {
            Log.d(TAG, "keyCodes: ${keyCodes.joinToString()}")
        } else {
            Log.d(TAG, "keyCodes is null")
        }

        // Optional: Commit the character to the input field for testing
        val ic = currentInputConnection
        ic?.commitText(primaryCode.toChar().toString(), 1)
    }

    override fun onPress(primaryCode: Int) {
        Log.d(TAG, "onPress called with primaryCode: $primaryCode")
    }
    override fun onRelease(primaryCode: Int) {
        Log.d(TAG, "onRelease called with primaryCode: $primaryCode")
    }
    override fun onText(text: CharSequence?) {}
    override fun swipeDown() {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeUp() {}
}