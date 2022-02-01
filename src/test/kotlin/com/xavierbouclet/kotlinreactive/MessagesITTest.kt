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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(R2dbcConfig::class)
@ExtendWith(PostgresSQLExtension::class)
@ContextConfiguration(initializers = [PostgresSQLExtension.Initializer::class])
class MessagesITTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var r2dbcEntityTemplate:R2dbcEntityTemplate

    lateinit var requestSpecification: RequestSpecification

    @BeforeEach
    fun beforeEach() {
        val logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)
        requestSpecification = RequestSpecBuilder().setPort(port).setBasePath("/").setRelaxedHTTPSValidation().setConfig(config).build()
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

    @Test
    fun `Delete a message`() {
        r2dbcEntityTemplate.delete(Message::class.java).all().block()

        val (id, _) = r2dbcEntityTemplate.insert(Message::class.java).using(Message(message = "My message to delete")).block()!!

        val response = Given {
            headers(emptyMap<String, String>())
            contentType(ContentType.JSON)
            spec(requestSpecification)
        } When {
            delete("/messages/$id")
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
            put("/messages/$id")
        } Then {
            statusCode(200)
        } Extract {
            `as`(Message::class.java)
        }
        assertThat(response.message).isEqualTo("My modified message")
        assertThat(response.id).isNotNull.isEqualTo(id)
    }
}