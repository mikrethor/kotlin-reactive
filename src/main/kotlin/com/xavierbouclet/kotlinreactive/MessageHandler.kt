package com.xavierbouclet.kotlinreactive

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*

@Component
class MessageHandler(private val messageService: MessageService) {

    suspend fun getAllMessages(request: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(messageService.getAllMessages())
    }

    suspend fun getMessageById(request: ServerRequest): ServerResponse =
        messageService.getMessageById(UUID.fromString(request.pathVariable("id"))).let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(it)
        }

    suspend fun create(request: ServerRequest): ServerResponse {
        val message = request.awaitBodyOrNull(Message::class)

        return message?.let {
            ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(
                    messageService.create(it)
                )
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = UUID.fromString(request.pathVariable("id"))

        return if (messageService.existsById(id)) {
            messageService.deleteById(id)
            ServerResponse.noContent().buildAndAwait()
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun modify(request: ServerRequest): ServerResponse {
        val message = request.awaitBodyOrNull(Message::class)
            ?: return ServerResponse.badRequest().buildAndAwait()

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(
                messageService.modify(
                    message
                )
            )
    }
}