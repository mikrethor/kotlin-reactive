package com.xavierbouclet.kotlinreactive

import java.util.*

class MessageService(private val messageRepository: MessageRepository) {

    fun getAllMessages() = messageRepository.findAll()

    suspend fun getMessageById(id: UUID) = messageRepository.findById(id)

    suspend fun create(message: Message) = messageRepository.save(message)

    suspend fun deleteById(id: UUID) = messageRepository.deleteById(id)

    suspend fun existsById(id: UUID) = messageRepository.existsById(id)

    suspend fun modify(message: Message) = messageRepository.save(message)

}