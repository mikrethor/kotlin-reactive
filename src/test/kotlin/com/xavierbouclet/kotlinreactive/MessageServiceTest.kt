package com.xavierbouclet.kotlinreactive

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

@ExtendWith(SpringExtension::class)
class MessageServiceTest {

    @MockkBean
    lateinit var messageRepository: MessageRepository

    private lateinit var messageService: MessageService

    @BeforeEach
    fun beforeEach() {
        messageService = MessageService(messageRepository)
    }

    @Test
    fun `getAllMessages should return 1 message`() = runBlocking {

        val message = Message(UUID.randomUUID(), "a message")

        every { messageRepository.findAll() } returns MutableStateFlow(message)

        val results = messageService.getAllMessages()

        assertThat(results.first()).isEqualTo(message)

        verify(exactly = 1) { messageRepository.findAll() }
    }
}