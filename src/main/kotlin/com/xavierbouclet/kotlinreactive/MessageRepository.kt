package com.xavierbouclet.kotlinreactive

import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import java.util.*


class MessageRepository(private val operations: R2dbcEntityOperations) {

    fun count() =
        operations.count(Query.empty(), Message::class.java)

    fun findAll() =
        operations.select(Query.empty(), Message::class.java)

    fun findById(id: UUID?) =
        operations.select(Message::class.java).matching(Query.query(Criteria.where("id").`is`(id!!))).one()

    fun deleteById(id: UUID?) =
        operations.delete(Message::class.java).matching(Query.query(Criteria.where("id").`is`(id!!))).all()

    fun deleteAll() =
        operations.delete(Message::class.java).all().then()

    fun update(message: Message) = operations.update(message)

    fun save(message: Message) = operations.insert(Message::class.java).using(message)


}

