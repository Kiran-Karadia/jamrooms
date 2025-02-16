package com.kiran.controllers

import com.kiran.services.RoomService
import io.micronaut.http.annotation.Controller

@Controller("/room")
class RoomController(
    private val roomService: RoomService,
) {

}