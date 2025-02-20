package com.kiran.controllers

import com.kiran.services.RoomService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.HttpResponse.unauthorized
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.CookieValue
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory

@Controller("/room")
class RoomController(
    private val roomService: RoomService,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Get("/create")
    suspend fun createRoom(
        @CookieValue("SESSION_ID") sessionId: String?
    ): HttpResponse<*> {
        if (sessionId.isNullOrEmpty()) {
            return unauthorized<String>().body("No session ID found, please login")
        }
        val room = roomService.createRoom(sessionId)
        logger.info("Room should have been created")
        return ok(room)
    }

    @Get("/{roomCode}")
    suspend fun joinRoom(
        @PathVariable roomCode: String,
    ): HttpResponse<*> {
        return ok(roomService.joinRoom(roomCode))
    }

    @Get("/{roomCode}/token")
    suspend fun getRoomToken(
        @PathVariable roomCode: String,
    ): HttpResponse<*> {
        val token = roomService.getRoomToken(roomCode)
        return ok(token)
    }

}