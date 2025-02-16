package com.kiran.services

import com.kiran.repositories.RoomRepository
import jakarta.inject.Singleton

@Singleton
class RoomService(
    val roomRepository: RoomRepository,
) {

}