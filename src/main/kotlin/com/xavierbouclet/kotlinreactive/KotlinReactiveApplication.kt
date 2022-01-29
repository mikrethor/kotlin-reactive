package com.xavierbouclet.kotlinreactive

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
    enable(dataConfig)
    beans { bean<MessageService>() }
    beans { bean<KotlinService>() }
    enable(webConfig)
    enable(initDbConfig)
    enable(r2dbcConfig)
}

fun main() {
    app.run()
}
