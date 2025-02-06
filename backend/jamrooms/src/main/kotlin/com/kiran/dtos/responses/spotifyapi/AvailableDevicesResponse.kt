package com.kiran.dtos.responses.spotifyapi

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AvailableDevicesResponse(
    val devices: List<Device>
)
