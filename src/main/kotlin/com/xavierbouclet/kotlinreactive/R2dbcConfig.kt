package com.xavierbouclet.kotlinreactive

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient


@Configuration(proxyBeanMethods = false)
class R2dbcConfig {

    @Bean
    fun repositoryFactory(client: DatabaseClient): R2dbcRepositoryFactory? {
        return R2dbcRepositoryFactory(client, DefaultReactiveDataAccessStrategy(PostgresDialect()))
    }

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(
            ResourceDatabasePopulator(
                ClassPathResource("install-uuid-ossp.sql"),
                ClassPathResource("schema.sql"),
                ClassPathResource("data.sql")
            )
        )
        return initializer
    }
}