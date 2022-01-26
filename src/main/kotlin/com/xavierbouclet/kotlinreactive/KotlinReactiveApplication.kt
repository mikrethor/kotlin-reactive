package com.xavierbouclet.kotlinreactive

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
    enable(dataConfig)
    beans { bean<MessageService>() }
    enable(webConfig)
    enable(initDbConfig)
}

fun main() {
    app.run()
}
