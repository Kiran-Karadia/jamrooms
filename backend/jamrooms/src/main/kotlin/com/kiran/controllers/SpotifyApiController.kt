package com.kiran.controllers

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import com.kiran.services.SpotifyApiService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.CookieValue
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory

@Controller("/spotify/api")
class SpotifyApiController(
    private val spotifyApiService: SpotifyApiService,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)
    @Get("/my-profile")
    suspend fun getUserProfile(
        @CookieValue("SESSION_ID") sessionId: String?
    ): HttpResponse<CurrentUserProfileResponse> {
        if (sessionId.isNullOrBlank()) { throw Exception("No sessionId found") }
        logger.info("Cookie found! SESSION_ID: $sessionId")
        val userProfile = spotifyApiService.getUserProfile(sessionId)
        return ok(userProfile)
    }

    @Get("/devices")
    suspend fun getDevices(
        @CookieValue("SESSION_ID") sessionId: String?
    ): HttpResponse<AvailableDevicesResponse> {
        if (sessionId.isNullOrBlank()) { throw Exception("No sessionId found") }
        val devices = spotifyApiService.getAvailableDevices(sessionId)
        return ok(devices)
    }

    @Get("/player")
    suspend fun getPlayer(
        @CookieValue("SESSION_ID") sessionId: String?
    ): HttpResponse<PlaybackStateResponse> {
        if (sessionId.isNullOrBlank()) { throw Exception("No sessionId found") }
        val player = spotifyApiService.getPlaybackState(sessionId)
        return ok(player)
    }


    @Get("/toggle")
    suspend fun toggle(
        @CookieValue("SESSION_ID") sessionId: String?
    ): HttpResponse<*> {
        if (sessionId.isNullOrBlank()) { throw Exception("No sessionId found") }
        spotifyApiService.togglePlayback(sessionId)
        return ok("Playback toggled")
    }
}