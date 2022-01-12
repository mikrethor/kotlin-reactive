package com.xavierbouclet.kotlinreactive

import org.springframework.core.io.ClassPathResource
import org.springframework.fu.kofu.configuration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

val initTestDbConfig = configuration {
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


