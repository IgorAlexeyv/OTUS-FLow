package ru.otus.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext


fun main(): Unit = runBlocking {
    launch(CoroutineName("Collector")) {
        namedFlow().collect { value -> println(value) }
    }
}

fun namedFlow(): Flow<String> = flow {
    for (i in 1..3) {
        // Emit coroutine name
        emit("At ${coroutineContext[CoroutineName]}: $i")
    }
}