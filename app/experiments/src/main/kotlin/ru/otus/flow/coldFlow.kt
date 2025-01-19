package ru.otus.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() = runBlocking {
    println("-- Subscription 1 --")
    randomFlow().collect { value -> println("Subscription 1: $value") }
    println("-- Subscription 2 --")
    randomFlow().collect { value -> println("Subscription 2: $value") }
}

fun randomFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(Random.nextInt(1, 10)) // Random value
    }
}