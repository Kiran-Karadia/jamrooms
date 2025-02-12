package com.kiran.httpclients

import com.kiran.dtos.responses.spotifyapi.AvailableDevicesResponse
import com.kiran.dtos.responses.spotifyapi.CurrentUserProfileResponse
import com.kiran.dtos.responses.spotifyapi.PlaybackStateResponse
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client

@Client("\${spotify.client-urls.api}")
interface SpotifyApiClient {

    @Get("/v1/me")
    suspend fun getCurrentUserProfile(
        @Header(HttpHeaders.AUTHORIZATION) auth: String
    ): CurrentUserProfileResponse

    @Get("/v1/player/devices")
    suspend fun getAvailableDevices(
        @Header(HttpHeaders.AUTHORIZATION) auth: String
    ): AvailableDevicesResponse

    @Get("/v1/me/player")
    suspend fun getPlaybackState(
        @Header(HttpHeaders.AUTHORIZATION) auth: String
    ): PlaybackStateResponse

    // TODO: Might not need this. getPlaybackState returns all and more info
//    @Get("/v1/me/player/currently-playing")
//    suspend fun getCurrentlyPlayingTrack(
//        @Header(HttpHeaders.AUTHORIZATION) auth: String
//    ): CurrentlyPlayingTrackResponse

    @Put("/v1/me/player/play")
    suspend fun startOrResumePlayback(
        @Header(HttpHeaders.AUTHORIZATION) auth: String
    )
}