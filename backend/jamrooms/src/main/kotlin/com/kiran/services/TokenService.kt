package com.kiran.services

import com.kiran.entities.SpotifyToken
import com.kiran.repositories.SpotifyTokenRepository
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.time.Instant

@Singleton
class TokenService(
    private val spotifyTokenRepository: SpotifyTokenRepository
) {

    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    suspend fun getAuthHeaderValue(): String {
        val spotifyToken = spotifyTokenRepository.findByUserId() ?: throw Exception("Token not found")
        return "${spotifyToken.tokenType} ${spotifyToken.accessToken}"
    }

    suspend fun saveSpotifyToken(spotifyToken: SpotifyToken): SpotifyToken {
        return spotifyTokenRepository.save(spotifyToken)
    }

    suspend fun isAuthenticated(userId: String): Boolean {
        val spotifyToken = spotifyTokenRepository.findByUserId(userId) ?: return false

        return Instant.now().isBefore(spotifyToken.updatedAt!!.plusSeconds(spotifyToken.expiresIn.toLong()))
        //TODO: Refresh logic still needed...
    }
}