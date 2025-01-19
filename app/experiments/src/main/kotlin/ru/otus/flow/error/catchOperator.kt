package ru.otus.flow.error

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val flowWithError = flow {
        emit(1)
        emit(2)
        throw NumberFormatException("Not a number")
    }.catch { e ->
        println("Caught exception: $e")
        emit(0)
    }

    flowWithError.collect { value -> println(value) }
}