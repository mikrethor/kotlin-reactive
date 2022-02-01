package com.xavierbouclet.kotlinreactive

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageRepository : CoroutineCrudRepository<Message, UUID>