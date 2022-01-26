package com.xavierbouclet.kotlinreactive

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerRequest
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
    fun `getAllMessages should return 1 message`() {

        val message = Message(UUID.randomUUID(), "a message")

        every { messageRepository.findAll() } returns Flux.just(message)

        StepVerifier
            .create(messageService.getAllMessages()).`as`("Retrieve all the messages")
            .expectNext(message)
            .verifyComplete()

        verify(exactly = 1) { messageRepository.findAll() }
    }
}