package com.kiran.controllers

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import com.kiran.httpclients.SpotifyApiClient
import com.kiran.repositories.SpotifyTokenRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put

@Controller("/spotify/api")
class SpotifyApiController(
    private val spotifyTokenRepository: SpotifyTokenRepository,
    private val spotifyApiClient: SpotifyApiClient,
) {
    private suspend fun getToken(): String {
        val token = spotifyTokenRepository.findByUserId()
        return "${token.tokenType} ${token.accessToken}"
    }
    @Get("/my-profile")
    suspend fun getUserProfile(): HttpResponse<CurrentUserProfileResponse> {
        val userProfile = spotifyApiClient.getCurrentUserProfile(getToken())
        return ok(userProfile)
    }

    @Get("/devices")
    suspend fun getDevices(): HttpResponse<AvailableDevicesResponse> {
        val devices = spotifyApiClient.getAvailableDevices(getToken())
        return ok(devices)
    }

    @Get("/player")
    suspend fun getPlayer(): HttpResponse<PlaybackStateResponse> {
        val player = spotifyApiClient.getPlaybackState(getToken())
        return ok(player)
    }

    @Put("/play")
    suspend fun play(): HttpResponse<*> {
        return spotifyApiClient.startOrResumePlayback(getToken())
    }

    @Put("/pause")
    suspend fun pauser(): HttpResponse<*> {
        return spotifyApiClient.pauserPlayback(getToken())
    }
}