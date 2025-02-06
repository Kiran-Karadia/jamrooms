package com.kiran.services

import com.kiran.httpclients.SpotifyApiClient
import jakarta.inject.Singleton

@Singleton
class SpotifyApiService(
    private val spotifyApiClient: SpotifyApiClient,
) {
}