package ru.otus.flow.error

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val flowWithError = flow {
        emit(1)
        emit(2)
        throw NumberFormatException("Not a number")
    }

    try {
        flowWithError.collect { value -> println(value) }
    } catch (e: NumberFormatException) {
        println("Caught exception: $e")
    }
}