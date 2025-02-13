package com.kiran.services

import com.kiran.repositories.SpotifyTokenRepository
import jakarta.inject.Singleton

@Singleton
class TokenService(
    private val spotifyTokenRepository: SpotifyTokenRepository
) {
    suspend fun getAuthHeaderValue(): String {
        val spotifyToken = spotifyTokenRepository.findByUserId()
        return "${spotifyToken.tokenType} ${spotifyToken.accessToken}"
    }
}