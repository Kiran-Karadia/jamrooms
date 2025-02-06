package com.kiran.dtos.responses.spotifyapi

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PlaybackStateResponse(
    val item: Item
)

@Serdeable
data class Item(
    val name: String,
    val artists: List<Artist>,
    val album: Album,

    @JsonProperty("duration_ms")
    val durationMs: Int
)

@Serdeable
data class Artist(
    val name: String
)

@Serdeable
data class Album(
    val images: List<AlbumImage>
)

@Serdeable
data class AlbumImage(
    val url: String,
    val height: Int,
    val width: Int
)