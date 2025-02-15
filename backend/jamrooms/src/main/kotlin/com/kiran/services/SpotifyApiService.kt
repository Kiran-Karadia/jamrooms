package com.kiran.services

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import com.kiran.httpclients.SpotifyApiClient
import jakarta.inject.Singleton

@Singleton
class SpotifyApiService(
    private val spotifyApiClient: SpotifyApiClient,
) {
    suspend fun getUserProfile(): CurrentUserProfileResponse {
        val currentUserProfileResponse = spotifyApiClient.getCurrentUserProfile()
        return currentUserProfileResponse
    }

    suspend fun getAvailableDevices(): AvailableDevicesResponse {
        val availableDevicesResponse = spotifyApiClient.getAvailableDevices()
        return availableDevicesResponse
    }

    suspend fun getPlaybackState(): PlaybackStateResponse {
        val playbackStateResponse = spotifyApiClient.getPlaybackState()
        return playbackStateResponse
    }

    suspend fun togglePlayback() {
        val playbackStateResponse = spotifyApiClient.getPlaybackState()
        when (playbackStateResponse.isPlaying) {
            true -> spotifyApiClient.pausePlayback()
            false -> spotifyApiClient.startOrResumePlayback()
        }
    }
}