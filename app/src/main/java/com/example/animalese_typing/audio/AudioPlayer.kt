package com.example.animalese_typing.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_GAME
import android.media.SoundPool
import com.example.animalese_typing.AnimaleseTyping.Companion.logMessage
import com.example.animalese_typing.R
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import kotlin.collections.iterator

/**
 * Dedicated Audio Player for sounds.
 */
object AudioPlayer {
    private var applicationContext: Context? = null
    private var keycodeMap: Map<Int, String>? = null

    // SoundPool
    private var soundPool: SoundPool? = null
    private val soundIdMap = mutableMapOf<String, Int>()
    private var isSoundPoolLoaded = false

    fun initialize(context: Context, initialVoiceProfilePrefix: String = "f1") {
        applicationContext = context.applicationContext

        initializeSoundPool()
        loadKeycodeMap()
    }

    private fun initializeSoundPool() {
        if (soundPool != null) return

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(USAGE_GAME)
            .setContentType(CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5) //TODO
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool?.setOnLoadCompleteListener { sp, sampleId, status ->
            if (soundIdMap.containsValue(sampleId)) isSoundPoolLoaded = true
        }
    }

    private fun loadSound(audioFile: String): Int {
        if (applicationContext == null || soundPool == null) return 0
        if (soundIdMap.containsKey(audioFile)) return soundIdMap[audioFile] ?: 0

        try {
            val resId = applicationContext!!.resources.getIdentifier(
                audioFile,
                "raw",
                applicationContext!!.packageName
            )
            if (resId != 0) {
                val soundId = soundPool!!.load(applicationContext!!, resId, 1)
                soundIdMap[audioFile] = soundId
                return soundId
            }
        } catch (e: Exception) {}
        return 0
    }

    fun loadKeycodeMap() {
        try {
            if (keycodeMap != null) return

            val tempMap = mutableMapOf<Int, String>()
            applicationContext?.resources?.openRawResource(R.raw.keycode_map).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    val stringWriter = StringWriter()
                    var c: Int
                    while(reader.read().also { c = it } != -1) stringWriter.write(c)

                    val jsonString = stringWriter.toString()
                    val jsonObject = JSONObject(jsonString)

                    for (key in jsonObject.keys()) {
                        val value = jsonObject.getString(key)
                        val parsedValue = when {
                            value == null -> ""
                            value.startsWith("sfx.") -> value.replaceFirst(".","_")
                            value.startsWith("&.") -> "f1_${value.substring(2)}"
                            else -> ""
                        }
                        loadSound( parsedValue )
                        tempMap[key.toInt()] = parsedValue
                    }
                    keycodeMap = tempMap.toMap()
                }
            }
            // logMessage("Keycode to Sound Map Loaded: $keycodeMap")
        }
        catch (e: Exception) {}
    }

    fun keycodeToSound(keycode: Int): String? {
        if (keycodeMap == null) return null
        return keycodeMap!![keycode]
    }

    fun playSound(audioFile: String?) {
        logMessage("Playing $audioFile $soundPool")
        if (audioFile == null || soundPool == null) playSound(keycodeToSound(0))
        val soundId = soundIdMap[audioFile]

        if (soundId != null && soundId != 0) {
            if (isSoundPoolLoaded || soundPool?.load(applicationContext!!,soundIdMap[audioFile] ?: 0,1) != 0 ) {
                soundPool?.play(soundId, 1f, 1f, 0, 0, 1f)
                logMessage("Audio Played $audioFile")
            }
        }
    }
}

