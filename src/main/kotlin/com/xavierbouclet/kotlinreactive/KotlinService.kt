package com.xavierbouclet.kotlinreactive

import reactor.core.publisher.Mono

class KotlinService {

    fun stringToMono(value: String) = Mono.just(value)

}