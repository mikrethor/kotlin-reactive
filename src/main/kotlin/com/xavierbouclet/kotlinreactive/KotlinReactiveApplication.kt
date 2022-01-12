package com.xavierbouclet.kotlinreactive

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
	enable(dataConfig)
	enable(webConfig)
	enable(initDbConfig)
}

fun main() {
	app.run()
}
