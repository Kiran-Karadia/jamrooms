package com.kiran.httpclients

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client

@Client(id = "spotify-api")
interface SpotifyApiClient {

    @Get("/v1/me")
    suspend fun getCurrentUserProfile(
    ): CurrentUserProfileResponse

    @Get("/v1/me/player/devices")
    suspend fun getAvailableDevices(
    ): AvailableDevicesResponse

    @Get("/v1/me/player")
    suspend fun getPlaybackState(
    ): PlaybackStateResponse

    // TODO: Might not need this. getPlaybackState returns all and more info
//    @Get("/v1/me/player/currently-playing")
//    suspend fun getCurrentlyPlayingTrack(
//        @Header(HttpHeaders.AUTHORIZATION) auth: String
//    ): CurrentlyPlayingTrackResponse

    @Put("/v1/me/player/play")
    suspend fun startOrResumePlayback(
    ): HttpResponse<*>

    @Put("/v1/me/player/pause")
    suspend fun pauserPlayback(
    ): HttpResponse<*>
}