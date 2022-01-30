package com.xavierbouclet.kotlinreactive

import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

fun coRoutes(messageHandler: MessageHandler) = coRouter {
    GET("/messages", messageHandler::getAllMessages)
    GET("/messages/{id}", messageHandler::getMessageById)
    POST("/messages", messageHandler::create)
    DELETE("/messages/{id}", messageHandler::delete)
    PUT("/messages", messageHandler::modify)
}

fun routes(javaHandler: JavaHandler) = router {
    GET("/hello", javaHandler::aJavaBean)
}


