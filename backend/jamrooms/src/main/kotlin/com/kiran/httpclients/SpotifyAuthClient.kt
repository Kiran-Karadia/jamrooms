package com.kiran.httpclients

import com.kiran.dtos.responses.spotfyauth.SpotifyTokenResponse
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${spotify.client-urls.auth}")
@Header(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
interface SpotifyAuthClient {

    @Post("/api/token")
    suspend fun getAccessToken(
        @Header(HttpHeaders.AUTHORIZATION) auth: String,
        // TODO: Data class doesn't deserialize into application_form_urlencoded, so use a map for now
        @Body tokenRequestBody: Map<String, String>
    ): SpotifyTokenResponse
}