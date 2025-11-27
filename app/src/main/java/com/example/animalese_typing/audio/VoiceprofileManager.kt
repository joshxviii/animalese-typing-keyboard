package com.example.animalese_typing.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_ASSISTANCE_SONIFICATION
import android.media.SoundPool
import android.util.Log

object VoiceProfileManager {
    private const val TAG = "VoiceProfileManager"
    private const val PREFS_NAME = "voice_prefs"
    private const val SELECTED_PROFILE_ID_KEY = "selected_profile_id"
    private const val DEFAULT_PROFILE_ID = "f1"

    private var soundPool: SoundPool? = null
    private var currentProfile: VoiceProfile? = null
    private var isSoundPoolInitialized = false
    private var soundsCurrentlyLoading = 0
    private var onAllSoundsLoadedListener: (() -> Unit)? = null

    private val availableProfiles = mutableMapOf<String, VoiceProfile>()

    fun initialize(context: Context) {
        if (isSoundPoolInitialized) return

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool?.setOnLoadCompleteListener { _, sampleId, status ->
            soundsCurrentlyLoading--
            if (status == 0) {
                Log.d(TAG, "SoundID $sampleId loaded.")
                if (soundsCurrentlyLoading == 0 && currentProfile != null) {
                    Log.d(TAG, "All sounds for profile ${currentProfile?.profileId} loaded.")
                    onAllSoundsLoadedListener?.invoke()
                }
            } else {
                Log.e(TAG, "Failed to load SoundID $sampleId, status: $status")
            }
            if (soundsCurrentlyLoading < 0) soundsCurrentlyLoading = 0
        }
        isSoundPoolInitialized = true

        //discoverAndParseProfiles(context) // Load profile definitions
        //loadSelectedProfile(context)      // Load sounds for the selected profile
    }

}
