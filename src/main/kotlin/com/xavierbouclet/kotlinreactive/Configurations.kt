package com.xavierbouclet.kotlinreactive

import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

val initDbConfig = configuration {
    beans {
        bean {
            ConnectionFactoryInitializer().apply {
                setConnectionFactory(ref())
                setDatabasePopulator(
                    ResourceDatabasePopulator(
                        ClassPathResource("install-uuid-ossp.sql"),
                        ClassPathResource("schema.sql"),
                        ClassPathResource("data.sql")
                    )
                )
            }
        }
    }
}

val dataConfig = configuration {
    beans {
        bean<R2dbcEntityTemplate>()
        bean<MessageRepository>()
    }
}

val r2dbcConfig = configuration {
    r2dbc {
        url = this@configuration.configurationProperties<DatabaseProperties>(prefix = "spring.r2dbc").url
        username = this@configuration.configurationProperties<DatabaseProperties>(prefix = "spring.r2dbc").username
        password = this@configuration.configurationProperties<DatabaseProperties>(prefix = "spring.r2dbc").password
    }
}

val webConfig = configuration {
    beans {
        bean<MessageHandler>()
        bean<JavaHandler>()
        bean(::routes)
        bean(::coRoutes)
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        codecs {
            string()
            jackson()
        }
    }
}

