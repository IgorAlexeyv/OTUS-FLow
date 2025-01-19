package ru.otus.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(): Unit = runBlocking {
    preservingContext().collect { value -> println(value) }
}

fun preservingContext(): Flow<String> = flow {
    val value = withContext(Dispatchers.IO) {
        delay(1000)
        "Value" // Return some value
    }
    emit(value) // Emit from original context
}