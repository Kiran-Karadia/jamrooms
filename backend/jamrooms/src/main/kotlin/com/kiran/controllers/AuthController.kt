package com.kiran.controllers

import com.kiran.repositories.SpotifyToken
import com.kiran.services.AuthService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/auth")
class AuthController(
    private val authService: AuthService,
) {
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
        val tokenResponse = authService.exchangeAuthCodeForToken(code)
        return HttpResponse.ok(tokenResponse)
    }

    @Get("/token")
    suspend fun saveToken(): HttpResponse<String> {
        authService.saveToken()
        return HttpResponse.ok()
    }

    @Get("/allTokens")
    suspend fun getAllTokens(): HttpResponse<List<SpotifyToken>> {
        return HttpResponse.ok(
            authService.getAllTokens()
        )
    }
}