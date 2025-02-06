package com.kiran.dtos.responses.spotifyapi

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CurrentUserProfileResponse(
    @JsonProperty("display_name")
    val displayName: String,

    val id: String
)
