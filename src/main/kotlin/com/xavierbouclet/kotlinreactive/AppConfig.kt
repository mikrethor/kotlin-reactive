package com.xavierbouclet.kotlinreactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory

@Configuration(proxyBeanMethods = false)
class AppConfig {


    @Bean
    fun messageRepository(factory: R2dbcRepositoryFactory): MessageRepository {
        return factory.getRepository(MessageRepository::class.java)
    }

    @Bean
    fun messageService(messageRepository: MessageRepository): MessageService {
        return MessageService(messageRepository)
    }

    @Bean
    fun kotlinService(): KotlinService {
        return KotlinService()
    }

    @Bean
    fun messageHandler(messageService:MessageService): MessageHandler {
        return MessageHandler(messageService)
    }

    @Bean
    fun javaHandler(kotlinService:KotlinService,helloProperties:HelloProperties): JavaHandler {
        return JavaHandler(kotlinService,helloProperties)
    }
}