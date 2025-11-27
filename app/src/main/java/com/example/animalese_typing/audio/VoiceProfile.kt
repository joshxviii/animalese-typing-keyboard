package com.example.animalese_typing.audio

data class VoiceProfile(
    val profileId: String,
    val displayName: String,
    val sounds: Map<String, String>
) {
    @Transient
    val loadedSoundIds: MutableMap<String, Int> = mutableMapOf()
}