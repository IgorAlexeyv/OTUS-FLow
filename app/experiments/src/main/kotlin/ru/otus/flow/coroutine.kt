package ru.otus.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    suspending().forEach { value -> println(value) }
}

suspend fun suspending(): List<Int> {
    delay(1000) // All values at once!
    return listOf(1, 2, 3)
}