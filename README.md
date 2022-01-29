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

## Try it out

Use the docker command `docker run --name messages-db -e POSTGRES_USER=sa -e POSTGRES_PASSWORD=sa -e POSTGRES_DB=messages-db -p 5432:5432 -d postgres` to create the db and start the application.

Some curl commands :

* `curl localhost:8080/hello`
* `curl localhost:8080/messages` GET, POST, PUT, DELETE