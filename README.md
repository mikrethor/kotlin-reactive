# Kotlin Reactive

Base project for my confoo 2022 presentation [confoo 2022 presentation](https://confoo.ca/fr/2022/news/cfp-open-yul2022).

## Generate the project

Go on  [Spring Initializr](https://start.spring.io/).

* Group : com.xavierbouclet
* Artifact : kotlin-reactive
* Project : Gradle Project
* Language : Kotlin, Java : 17
* Dependencies :
  ** Spring Reactive Web
  ** Spring Data R2DBC

```
spring init \
     -b=2.6.3 \
     -g=com.xavierbouclet \
     -a=kotlin-reactive \
     --name=KotlinReactive \
     -j=17 \
     -d=webflux,data-r2dbc,postgresql,testcontainers \
     --build=gradle \
     -l=kotlin kotlin-reactive && cd kotlin-reactive && idea .
```

   testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
   }
   testImplementation("com.ninja-squad:springmockk:3.1.0")


val restAssuredVersion by extra("4.5.0")

    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testImplementation("io.rest-assured:spring-mock-mvc-kotlin-extensions:$restAssuredVersion")


## Try it out

Use the docker command `docker run --name messages-db -e POSTGRES_USER=sa -e POSTGRES_PASSWORD=sa -e POSTGRES_DB=messages-db -p 5432:5432 -d postgres` to create the db and start the application.

Some curl commands :

* `curl localhost:8080/hello`
* `curl localhost:8080/messages` GET, POST, PUT, DELETE
* curl -X POST -H "Content-Type: application/json" -d '{"message": "test post"}' http://localhost:8080/messages

## Test example with Mockito instead of Mockk

```kotlin
    @Test
    fun `getAllMessages should return 1 message`() = runBlocking {

        val message = Message(UUID.randomUUID(), "a message")

        Mockito.`when`(messageRepository.findAll()).thenReturn(MutableStateFlow(message))

        val results = messageService.getAllMessages()

        assertThat(results.first()).isEqualTo(message)

        Mockito.verify(messageRepository,times(1)).findAll()
    }

```