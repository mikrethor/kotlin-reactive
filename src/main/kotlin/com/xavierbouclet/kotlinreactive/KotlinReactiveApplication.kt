package com.xavierbouclet.kotlinreactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.fu.kofu.reactiveWebApplication

//val app = reactiveWebApplication {
//    enable(dataConfig)
//    beans { bean<MessageService>() }
//    beans { bean<KotlinService>() }
//    enable(webConfig)
//    enable(initDbConfig)
//    enable(r2dbcConfig)
//}
//
//fun main() {
//    app.run()
//}

@SpringBootApplication
class KotlinReactiveApplication

fun main(args: Array<String>) {
    runApplication<KotlinReactiveApplication>(*args)
}

