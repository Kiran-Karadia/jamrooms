package com.kiran.controllers

import com.kiran.entities.SpotifyToken
import com.kiran.services.AuthService
import com.kiran.services.TokenService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.cookie.Cookie
import io.micronaut.http.cookie.SameSite
import org.slf4j.LoggerFactory
import java.util.UUID

@Controller("/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenService: TokenService,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Get("/login")
    fun login(): HttpResponse<String> {
        val spotifyAuthRequest = authService.getAuthorizationUrl()
        return HttpResponse.redirect(spotifyAuthRequest)
    }

    @Get("/callback")
    suspend fun callback(
        @QueryValue code: String,
        @QueryValue state: String,
    ): HttpResponse<*> {
        val response = authService.exchangeAuthCodeForToken(code)

        val sessionId = UUID.randomUUID().toString()

        val savedToken = tokenService.saveSpotifyToken(
            SpotifyToken(
                sessionId = sessionId,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                tokenType = response.tokenType,
                expiresIn = response.expiresIn
            )
        )

        val cookie = Cookie.of("SESSION_ID", sessionId)
            .httpOnly(true)
            .secure(true)
            .sameSite(SameSite.Lax)
            .maxAge(3600)
            .path("/")

        logger.info("Cookie created. SessionID = $sessionId")

        return ok(savedToken)
            .cookie(cookie)
    }
}