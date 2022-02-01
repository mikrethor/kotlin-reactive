package com.xavierbouclet.kotlinreactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration(proxyBeanMethods = false)
class Routes {
    @Bean
    fun corouter(messageHandler: MessageHandler) = coRouter {
        "/messages".nest {
            GET("", messageHandler::getAllMessages)
            GET("/{id}", messageHandler::getMessageById)
            POST("", messageHandler::create)
            PUT("/{id}", messageHandler::modify)
            DELETE("/{id}", messageHandler::delete)
        }
    }

    @Bean
    fun router(javaHandler: JavaHandler) = router {
        GET("/hello", javaHandler::aJavaBean)
    }
}

