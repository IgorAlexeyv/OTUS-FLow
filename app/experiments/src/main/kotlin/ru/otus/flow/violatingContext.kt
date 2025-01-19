package ru.otus.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(): Unit = runBlocking {
    violatingContext().collect { value -> println(value) }
}

fun violatingContext(): Flow<String> = flow {
    withContext(Dispatchers.IO) {
        delay(1000)
        emit("Value") // Emit value from another context
    }
}