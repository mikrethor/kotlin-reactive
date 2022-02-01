package com.xavierbouclet.kotlinreactive

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class KotlinService {

    fun stringToMono(value: String) = Mono.just(value)

}