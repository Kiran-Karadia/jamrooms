package com.kiran.controllers

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import com.kiran.services.SpotifyApiService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/spotify/api")
class SpotifyApiController(
    private val spotifyApiService: SpotifyApiService,
) {
    @Get("/my-profile")
    suspend fun getUserProfile(): HttpResponse<CurrentUserProfileResponse> {
        val userProfile = spotifyApiService.getUserProfile()
        return ok(userProfile)
    }

    @Get("/devices")
    suspend fun getDevices(): HttpResponse<AvailableDevicesResponse> {
        val devices = spotifyApiService.getAvailableDevices()
        return ok(devices)
    }

    @Get("/player")
    suspend fun getPlayer(): HttpResponse<PlaybackStateResponse> {
        val player = spotifyApiService.getPlaybackState()
        return ok(player)
    }


    @Get("/toggle")
    suspend fun toggle(): HttpResponse<*> {
        spotifyApiService.togglePlayback()
        return ok("Playback toggled")
    }
}