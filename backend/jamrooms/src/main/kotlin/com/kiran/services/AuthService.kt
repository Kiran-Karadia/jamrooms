package com.kiran.services

import com.kiran.configs.AppConfig
import com.kiran.dtos.responses.spotfyauth.SpotifyTokenResponse
import com.kiran.entities.SpotifyToken
import com.kiran.httpclients.SpotifyAuthClient
import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.*

@Singleton
class AuthService(
    private val spotifyAuthClient: SpotifyAuthClient,
    private val tokenService: TokenService,
    private val appConfig: AppConfig,
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun getAuthorizationUrl(): URI {
        logger.info("getAuthorizationUrl called...")
        val authRequest = UriBuilder.of("${appConfig.spotify.urls.auth}")
            .queryParam("client_id", appConfig.spotify.clientId)
            .queryParam("response_type", "code")
            .queryParam("redirect_uri", appConfig.redirectUrl)
            .queryParam("show_dialog", "false")
            .queryParam("state", "some state") // TODO: Use this to redirect back to original url?
            .queryParam("scope",
                "user-read-playback-state user-modify-playback-state user-read-currently-playing streaming"
            )
            .build()
        logger.info("appConfig.redirectUrl: ${appConfig.redirectUrl}")
        logger.info("authRequest: $authRequest")
        return authRequest
    }

    suspend fun exchangeAuthCodeForToken(code: String): SpotifyTokenResponse {
        val tokenRequestBody = mapOf(
            "grant_type" to "authorization_code",
            "code" to code,
            "redirect_uri" to appConfig.redirectUrl
        )

        val getTokenResponse = spotifyAuthClient.getAccessToken(getAuthHeader(), tokenRequestBody)

        tokenService.saveSpotifyToken(
            SpotifyToken(
                userId = "KwKiran",
                accessToken = getTokenResponse.accessToken,
                refreshToken = getTokenResponse.refreshToken,
                tokenType = getTokenResponse.tokenType,
                expiresIn = getTokenResponse.expiresIn
            )
        )
        return getTokenResponse
    }

    private fun getAuthHeader(): String {
        val clientIdAndSecret = "${appConfig.spotify.clientId}:${appConfig.spotify.clientSecret}"
        val encodedClientIdAndSecret = Base64.getEncoder().encodeToString(clientIdAndSecret.toByteArray())
        return "Basic $encodedClientIdAndSecret"
    }
}