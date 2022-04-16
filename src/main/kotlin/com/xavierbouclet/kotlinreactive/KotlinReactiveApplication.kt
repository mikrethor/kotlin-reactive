package com.xavierbouclet.kotlinreactive

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
    enable(dataConfig)
    enable(weatherConfig)
    beans { bean<MessageService>() }
    beans { bean<WeatherService>() }
    enable(webConfig)
    enable(initDbConfig)
    enable(r2dbcConfig)
}

fun main() {
    app.run()
}
