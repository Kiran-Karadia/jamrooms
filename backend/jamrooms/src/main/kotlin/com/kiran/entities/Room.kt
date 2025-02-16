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
) {
}
