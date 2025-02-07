package com.kiran.services

import com.kiran.dtos.responses.spotfyauth.SpotifyTokenResponse
import com.kiran.httpclients.SpotifyAuthClient
import com.kiran.repositories.SpotifyToken
import com.kiran.repositories.SpotifyTokenRepository
import io.micronaut.context.annotation.Value
import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.net.URI
import java.util.*

@Singleton
class AuthService(
    private val spotifyAuthClient: SpotifyAuthClient,
    private val spotifyTokenRepository: SpotifyTokenRepository,
) {

    @Value("\${spotify.client-secret}")
    lateinit var clientSecret: String

    @Value("\${spotify.client-id}")
    lateinit var clientId: String

    fun getAuthorizationUrl(): URI {
        val authRequest = UriBuilder.of("https://accounts.spotify.com/authorize")
            .queryParam("client_id", clientId)
            .queryParam("response_type", "code")
            .queryParam("redirect_uri", "http://localhost:8080/auth/callback")
            .queryParam("show_dialog", "false")
            .queryParam("state", "some state") // TODO: Use this to redirect back to original url?
            .queryParam("scope",
                "user-read-playback-state user-modify-playback-state user-read-currently-playing streaming"
            )
            .build()
        return authRequest
    }

    suspend fun exchangeAuthCodeForToken(code: String): SpotifyTokenResponse {
        val tokenRequestBody = mapOf(
            "grant_type" to "authorization_code",
            "code" to code,
            "redirect_uri" to "http://localhost:8080/auth/callback"
        )

        val getTokenResponse = spotifyAuthClient.getAccessToken(getAuthHeader(), tokenRequestBody)
        return getTokenResponse
    }

    private fun getAuthHeader(): String {
        val clientIdAndSecret = "$clientId:$clientSecret"
        val encodedClientIdAndSecret = Base64.getEncoder().encodeToString(clientIdAndSecret.toByteArray())
        return "Basic $encodedClientIdAndSecret"
    }

    suspend fun saveToken() {
        val token = SpotifyToken(
            userId = "Kiran",
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            tokenType = "Bearer",
            expiresIn = 3600
        )

        spotifyTokenRepository.save(token)
    }

    suspend fun getAllTokens(): List<SpotifyToken> {
        return spotifyTokenRepository.findAll().toList()
    }
}