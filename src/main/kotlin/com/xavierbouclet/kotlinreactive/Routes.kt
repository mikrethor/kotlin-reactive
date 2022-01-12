package com.xavierbouclet.kotlinreactive

import org.springframework.web.reactive.function.server.router

fun routes(messageHandler: MessageHandler) = router {
    GET("/messages", messageHandler::getAllMessages)
    GET("/messages/{id}", messageHandler::getMessageById)
    POST("/messages", messageHandler::create)
    DELETE("/messages/{id}", messageHandler::delete)
    PUT("/messages", messageHandler::modify)
}
