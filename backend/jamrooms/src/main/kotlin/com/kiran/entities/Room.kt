package com.kiran.entities

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.event.PrePersist
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant

@Serdeable
@MappedEntity
data class Room(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,

    var hostSessionId: String,

    var roomCode: String? = null,

    @DateCreated
    var createdAt: Instant? = null,

) {
    @PrePersist
    fun generateRoomCode() {
        roomCode = (1..6)
            .map { ('A'..'Z').random() }
            .joinToString("")
    }
}
