package ru.otus.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Concurrent execution
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }

    // Collect the flow
    intFlow().collect { value -> println(value) }
}

fun intFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // Computing...
        emit(i) // emit next value
    }
}
