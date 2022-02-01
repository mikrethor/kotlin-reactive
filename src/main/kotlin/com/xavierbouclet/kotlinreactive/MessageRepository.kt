package com.xavierbouclet.kotlinreactive

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.bind
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MessageRepository(private val client: DatabaseClient) {

    suspend fun count(): Long =
        client.sql("SELECT COUNT(id) FROM message")
            .map { row -> (row.get(0) as Long) }
            .first().awaitSingle()


    fun findAll() =
        client.sql("SELECT id, message FROM message")
            .map { row ->
                Message(
                    id = row.get("id", UUID::class.java)!!,
                    message = row.get("message", String::class.java)!!
                )
            }
            .all()
            .asFlow()

    suspend fun findById(id: UUID): Message =
        id.let {
            client.sql("SELECT id, message from message where id = :id")
                .bind("id", it)
                .map { row ->
                    Message(
                        id = row.get("id", UUID::class.java)!!,
                        message = row.get("message", String::class.java)!!
                    )
                }
                .first().awaitSingle()
        }


    suspend fun deleteAll(): Void =
        client.sql("DELETE FROM message").then().awaitSingle()

    suspend fun deleteById(id: UUID): UUID =
        client.sql("DELETE FROM message where id=:id RETURNING *").bind("id", id)
            .map { row ->

                row.get("id", UUID::class.java)!!


            }
            .first().awaitSingle()


    suspend fun existsById(id: UUID): Boolean =
        client.sql("SELECT count(id) FROM message WHERE id = :id").bind("id", id)
            .map { _ ->
                true
            }.first().awaitSingle()

    suspend fun insert(message: Message): Message {
        val messageToStore =
            if (message.id == null) message.copy(id = UUID.randomUUID()) else message

        return client.sql("INSERT INTO message(id, message) values(:id, :message) RETURNING *")
            .bind("id", messageToStore.id)
            .bind("message", messageToStore.message)
            .map { row ->
                Message(
                    row.get("id", UUID::class.java)!!,
                    row.get("message", String::class.java)!!
                )
            }
            .first().awaitSingle()
    }

    suspend fun update(message: Message) =
        client.sql("UPDATE message SET id=:id, message=:message WHERE id=:id RETURNING *")
            .bind("id", message.id)
            .bind("message", message.message)
            .map { row ->
                Message(
                    row.get("id", UUID::class.java)!!,
                    row.get("message", String::class.java)!!
                )
            }
            .first().awaitSingle()
}

