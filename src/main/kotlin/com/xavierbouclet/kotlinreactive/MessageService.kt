package com.xavierbouclet.kotlinreactive

import java.util.*

class MessageService(private val messageRepository: MessageRepository) {

    fun getAllMessages() = messageRepository.findAll()

    fun getMessageById(id: UUID) = messageRepository.findById(id)

    fun create(message: Message) = messageRepository.save(message)

    fun delete(id: UUID) = messageRepository.deleteById(id)

    fun modify(message: Message) = messageRepository.update(message)

}