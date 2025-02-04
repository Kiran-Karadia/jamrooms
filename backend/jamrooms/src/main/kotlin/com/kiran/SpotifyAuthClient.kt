package com.kiran

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable

@Client("https://accounts.spotify.com") // TODO: Don't have this hardcoded
@Header(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
interface SpotifyAuthClient {

    @Post("/api/token")
    suspend fun getAccessToken(
        @Header(HttpHeaders.AUTHORIZATION) auth: String,
        // TODO: Data class doesn't deserialize into application_form_urlencoded, so use a map for now
        @Body tokenRequestBody: Map<String, String>
    ): SpotifyTokenResponse
}

@Serdeable
data class SpotifyTokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("scope") val scope: String,
    @JsonProperty("expires_in") val expiresIn: Int,
    @JsonProperty("refresh_token") val refreshToken: String
)