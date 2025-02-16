package com.kiran.repositories

import com.kiran.entities.SpotifyToken
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface SpotifyTokenRepository: CoroutineCrudRepository<SpotifyToken, Long> {
    suspend fun findByUserId(userId: String = "KwKiran"): SpotifyToken?
}

