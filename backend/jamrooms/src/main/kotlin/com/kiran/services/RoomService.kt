package com.kiran.services

import com.kiran.entities.Room
import com.kiran.repositories.RoomRepository
import jakarta.inject.Singleton

@Singleton
class RoomService(
    private val roomRepository: RoomRepository,
    private val tokenService: TokenService
) {
    suspend fun createRoom(sessionId: String): Room {
        if (!tokenService.isAuthenticated(sessionId)) {
            throw Exception("No token found with that sessionId, please authenticate again...")
        }
        return roomRepository.save(
            Room(
                hostSessionId = sessionId
            )
        )
    }

    suspend fun joinRoom(roomCode: String): Room {
        val room = roomRepository.findByRoomCode(roomCode) ?: throw Exception("Room doesn't exist!")
        return room
    }

    suspend fun getRoomToken(roomCode: String): String {
        val token = roomRepository.findAccessTokenByRoomCode(roomCode) ?: throw Exception("Room doesn't exist or problem getting token")
        return token
    }
}