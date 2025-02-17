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
    suspend fun getUserProfile(sessionId: String): CurrentUserProfileResponse {
        val currentUserProfileResponse = spotifyApiClient.getCurrentUserProfile(sessionId)
        return currentUserProfileResponse
    }

    suspend fun getAvailableDevices(sessionId: String): AvailableDevicesResponse {
        val availableDevicesResponse = spotifyApiClient.getAvailableDevices(sessionId)
        return availableDevicesResponse
    }

    suspend fun getPlaybackState(sessionId: String): PlaybackStateResponse {
        val playbackStateResponse = spotifyApiClient.getPlaybackState(sessionId)
        return playbackStateResponse
    }

    suspend fun togglePlayback(sessionId: String) {
        val playbackStateResponse = spotifyApiClient.getPlaybackState(sessionId)
        when (playbackStateResponse.isPlaying) {
            true -> spotifyApiClient.pausePlayback(sessionId)
            false -> spotifyApiClient.startOrResumePlayback(sessionId)
        }
    }
}