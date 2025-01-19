package ru.otus.flow.error

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val flowWithError = flow {
        emit(1)
        emit(2)
        emit(3)
    }

    try {
        flowWithError.collect { throw NumberFormatException("Faulty collector") }
    } catch (e: NumberFormatException) {
        println("Caught exception: $e")
    }
}