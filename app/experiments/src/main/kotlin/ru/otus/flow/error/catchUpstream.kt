package ru.otus.flow.error

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val flow = flowOf(1, 2, 3)
        .map { if (2 == it) throw NumberFormatException("Faulty number 2") else it }
        .catch { e ->
            println("Caught exception: $e")
            emit(3)
        }
        .map { if (3 == it) throw NumberFormatException("Faulty number 3") else it }

    flow.collect { value -> println(value) }
}