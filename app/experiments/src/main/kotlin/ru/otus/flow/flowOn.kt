package ru.otus.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

fun main(): Unit = runBlocking(CoroutineName("Collect")) {
    flowingOnAnotherContext().collect { value ->
        println("Print on ${coroutineContext[CoroutineName]}: $value")
    }
}

fun flowingOnAnotherContext(): Flow<String> = flow {
    delay(1000)
    emit("Value from: ${coroutineContext[CoroutineName]}")
}.flowOn(Dispatchers.IO + CoroutineName("Flow"))