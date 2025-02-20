package com.kiran.entities

import io.micronaut.data.annotation.*
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant

@Serdeable
@MappedEntity
data class SpotifyToken(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,

    var sessionId: String,

    var accessToken: String,

    var refreshToken: String,

    var tokenType: String,

    // From spotify documentation: The time period (in seconds) for which the access token is valid.
    var expiresIn: Int,

    @DateCreated
    var createdAt: Instant? = null,

    @DateUpdated
    var updatedAt: Instant? = null,
)