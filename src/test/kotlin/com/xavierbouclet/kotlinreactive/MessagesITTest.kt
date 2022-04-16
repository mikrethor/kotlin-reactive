package com.xavierbouclet.kotlinreactive

import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.reactiveWebApplication
import org.testcontainers.containers.PostgreSQLContainer
import kotlin.properties.Delegates

class MessagesITTest {

    lateinit var requestSpecification: RequestSpecification

    open class SpecifiedPostgresSQLContainer(imageName: String) : PostgreSQLContainer<SpecifiedPostgresSQLContainer>(imageName)


    companion object {
        lateinit var application: ConfigurableApplicationContext
        var portValue by Delegates.notNull<Int>()

        val postgresSQLContainer = SpecifiedPostgresSQLContainer("postgres:14.1").withDatabaseName("test-messages-db")
            .withUsername("sa")
            .withPassword("sa")
            .withExposedPorts(5432)

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgresSQLContainer.start()

            val testR2DBCConfig = configuration {
                r2dbc {
                    url = postgresSQLContainer.jdbcUrl.replace("jdbc", "r2dbc")
                    username = "sa"
                    password = "sa"
                }
            }
            val testApp = reactiveWebApplication {
                enable(testR2DBCConfig)
                enable(dataConfig)
                beans { bean<MessageService>() }
                enable(webConfig)
                enable(initTestDbConfig)
            }
            application = testApp.run(profiles = "test")
            val ports = application.environment.propertySources.get("server.ports")
            val port = ports?.source as Map<*, *>
            portValue = port["local.server.port"] as Int

        }

        @JvmStatic
        @AfterAll
        fun afterClass() {
            postgresSQLContainer.stop()
        }
    }

    @BeforeEach
    fun beforeEach() {
        val logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)
        requestSpecification = RequestSpecBuilder().setPort(portValue).setBasePath("/").setRelaxedHTTPSValidation().setConfig(config).build()
    }

    @Test
    fun `Get a specific message`() {
        val response = Given {
            headers(emptyMap<String, String>())
            contentType(ContentType.JSON)
            spec(requestSpecification)
        } When {
            get("/messages/7cff6a38-a7cc-4478-883a-3edfa0232bca")
        } Then {
            statusCode(200)
        } Extract {
            asPrettyString()
        }

        assertThat(response).isEqualTo(
            """{
    "id": "7cff6a38-a7cc-4478-883a-3edfa0232bca",
    "message": "Hello Reader!"
}"""
        )
    }

    @Test
    fun `Get all messages`() {
        with(application) {

            val r2dbcEntityTemplate = getBean<R2dbcEntityTemplate>()
            r2dbcEntityTemplate.delete(Message::class.java).all().block()

            val message1 = r2dbcEntityTemplate.insert(Message::class.java).using(Message(message = "Hello !")).block()!!
            val message2 = r2dbcEntityTemplate.insert(Message::class.java).using(Message(message = "Hello Reader!")).block()!!

            val messages = listOf(message1, message2)


            val response = Given {
                headers(emptyMap<String, String>())
                contentType(ContentType.JSON)
                spec(requestSpecification)
            } When {
                get("/messages")
            } Then {
                statusCode(200)
            } Extract {
                `as`(Array<Message>::class.java);
            }

            assertThat(response.size).isEqualTo(2)
            assertThat(response[0]).isEqualTo(messages[0])
            assertThat(response[1]).isEqualTo(messages[1])
        }
    }

    @Test
    fun `Delete a message`() {
        val response = Given {
            headers(emptyMap<String, String>())
            contentType(ContentType.JSON)
            spec(requestSpecification)
        } When {
            delete("/messages/c2c10312-6b64-478c-be14-f227eea3a767")
        } Then {
            statusCode(204)
        } Extract {
            asPrettyString()
        }
        assertThat(response).isEmpty()
    }

    @Test
    fun `Create a message`() {
        val body = """{
    "message": "I have been created"
}"""
        val response = Given {
            headers(emptyMap<String, String>())
            contentType(ContentType.JSON)
            spec(requestSpecification)
            body(body)
        } When {
            post("/messages")
        } Then {
            statusCode(201)
        } Extract {
            `as`(Message::class.java)
        }
        assertThat(response.message).isEqualTo("I have been created")
        assertThat(response.id).isNotNull
    }

    @Test
    fun `Modify a message`() {
        with(application) {

            val r2dbcEntityTemplate = getBean<R2dbcEntityTemplate>()
            r2dbcEntityTemplate.delete(Message::class.java).all().block()

            val (id, _) = r2dbcEntityTemplate.insert(Message::class.java).using(Message(message = "My message to modify")).block()!!

            val body =
                """{
        "id": "$id",
        "message": "My modified message"
    }"""

            val response = Given {
                headers(emptyMap<String, String>())
                contentType(ContentType.JSON)
                spec(requestSpecification)
                body(body)
            } When {
                put("/messages")
            } Then {
                statusCode(200)
            } Extract {
                `as`(Message::class.java)
            }
            assertThat(response.message).isEqualTo("My modified message")
            assertThat(response.id).isNotNull.isEqualTo(id)
        }
    }
}