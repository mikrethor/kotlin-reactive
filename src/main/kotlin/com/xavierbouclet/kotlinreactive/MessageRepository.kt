package com.xavierbouclet.kotlinreactive

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface MessageRepository : CoroutineCrudRepository<Message, UUID>