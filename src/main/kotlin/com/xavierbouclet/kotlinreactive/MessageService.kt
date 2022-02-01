package com.xavierbouclet.kotlinreactive

import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun getAllMessages() = messageRepository.findAll()

    suspend fun getMessageById(id: UUID) = messageRepository.findById(id)

    suspend fun create(message: Message) = messageRepository.insert(message)

    suspend fun deleteById(id: UUID) = messageRepository.deleteById(id)

    suspend fun existsById(id: UUID) = messageRepository.existsById(id)

    suspend fun modify(message: Message) = messageRepository.update(message)

}