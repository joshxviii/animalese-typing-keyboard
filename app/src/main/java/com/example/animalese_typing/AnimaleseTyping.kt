package com.example.animalese_typing

import android.app.Application
import android.util.Log

class AnimaleseTyping: Application() {

    companion object {
        const val TAG_ID = "AnimaleseTyping"
        fun logMessage(message: String) {
            Log.d(TAG_ID, message)
        }
    }

    override fun onCreate() {
        super.onCreate()

        val context = this.applicationContext

        AudioPlayer.initialize(context)
    }
}