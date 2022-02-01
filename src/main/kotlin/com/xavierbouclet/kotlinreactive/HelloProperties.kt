package com.xavierbouclet.kotlinreactive

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "hello")
data class HelloProperties @ConstructorBinding constructor(val message: String)


