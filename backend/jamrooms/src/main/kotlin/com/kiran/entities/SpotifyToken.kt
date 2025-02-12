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

    var userId: String,

    @DateCreated
    var createdAt: Instant? = null,

    @DateUpdated
    var updatedAt: Instant? = null,

    var accessToken: String,

    var refreshToken: String,

    var tokenType: String,

    var expiresIn: Int
)
