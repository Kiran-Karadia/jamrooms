package com.kiran.dtos.responses.spotifyapi

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Device(
    val id: String,

    @JsonProperty("is_active")
    val isActive: Boolean,

    val name: String,

    @JsonProperty("volume_percent")
    val volumePercent: Int
)
