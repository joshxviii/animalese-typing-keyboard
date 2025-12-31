package com.example.animalese_typing

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.emoji2.text.EmojiCompat
import com.example.animalese_typing.audio.AudioPlayer

class AnimaleseTyping: Application() {

    companion object {
        const val TAG_ID = "AnimaleseTyping"
        var appInfo : PackageInfo = PackageInfo()

        fun logMessage(message: String) {
            Log.d(TAG_ID, message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context = this.applicationContext
        appInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_META_DATA)
        logMessage("Version: ${appInfo.versionName}")

        AudioPlayer.initialize(context)
        EmojiCompat.init(context)
    }
}