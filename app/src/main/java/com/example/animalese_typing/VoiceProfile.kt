package com.example.animalese_typing

data class VoiceProfile(
    val profileId: String,
    val displayName: String,
    val sounds: Map<String, String>
) {
    @Transient
    val loadedSoundIds: MutableMap<String, Int> = mutableMapOf()
}