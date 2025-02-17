package com.kiran.httpclients

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client

@Client(id = "spotify-api")
interface SpotifyApiClient {

    @Get("/v1/me")
    suspend fun getCurrentUserProfile(
        @Header(name = "X-Session-Id") sessionId: String,
    ): CurrentUserProfileResponse

    @Get("/v1/me/player/devices")
    suspend fun getAvailableDevices(
        @Header(name = "X-Session-Id") sessionId: String,
    ): AvailableDevicesResponse

    @Get("/v1/me/player")
    suspend fun getPlaybackState(
        @Header(name = "X-Session-Id") sessionId: String,
    ): PlaybackStateResponse

    @Put("/v1/me/player/play")
    suspend fun startOrResumePlayback(
        @Header(name = "X-Session-Id") sessionId: String,
    ): HttpResponse<*>

    @Put("/v1/me/player/pause")
    suspend fun pausePlayback(
        @Header(name = "X-Session-Id") sessionId: String,
    ): HttpResponse<*>


    // TODO: Might not need this. getPlaybackState returns all and more info
//    @Get("/v1/me/player/currently-playing")
//    suspend fun getCurrentlyPlayingTrack(
//        @Header(HttpHeaders.AUTHORIZATION) auth: String
//    ): CurrentlyPlayingTrackResponse
}