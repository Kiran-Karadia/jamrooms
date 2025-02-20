package com.kiran.repositories

import com.kiran.entities.Room
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface RoomRepository: CoroutineCrudRepository<Room, Long> {

    suspend fun findByRoomCode(roomCode: String): Room?

    @Query("""
        SELECT st.access_token
        FROM room r
        JOIN spotify_token st ON r.host_session_id = st.session_id
        WHERE r.room_code = :roomCode
    """)
    suspend fun findAccessTokenByRoomCode(roomCode: String): String?

}