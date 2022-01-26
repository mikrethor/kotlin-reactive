package com.xavierbouclet.kotlinreactive

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import java.util.*

class MessageHandler(private val messageService: MessageService) {

    fun getAllMessages(request: ServerRequest) = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(messageService.getAllMessages(), Message::class.java)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun getMessageById(request: ServerRequest) = ServerResponse
        .ok()
        .body(messageService.getMessageById(UUID.fromString(request.pathVariable("id"))), Message::class.java)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun create(request: ServerRequest) =
        request.bodyToMono(Message::class.java)
            .flatMap { message ->
                ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(messageService.create(message), Message::class.java)
            }

    fun delete(request: ServerRequest) = ServerResponse
        .status(HttpStatus.NO_CONTENT)
        .contentType(MediaType.APPLICATION_JSON)
        .body(messageService.delete(UUID.fromString(request.pathVariable("id"))), Void::class.java)
        .switchIfEmpty(ServerResponse.notFound().build())

    fun modify(request: ServerRequest) = request.bodyToMono(Message::class.java)
        .flatMap { message ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(messageService.modify(message), Message::class.java)
        }
}