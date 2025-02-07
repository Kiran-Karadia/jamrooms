package com.kiran.repositories

import io.micronaut.data.annotation.*
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
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

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface SpotifyTokenRepository: CoroutineCrudRepository<SpotifyToken, Long> {

}

