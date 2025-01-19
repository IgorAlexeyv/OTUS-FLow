package ru.otus.flow

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val stateFlow = MutableStateFlow(0)

    val collector = launch {
        stateFlow.collect { value -> println("Collected: $value") }
    }

    val lateCollector = launch {
        delay(75)
        stateFlow.collect { value -> println("Late collected: $value") }
    }

    // region Emit values
    delay(50)
    stateFlow.emit(1)
    delay(50)
    stateFlow.emit(2)
    delay(50)
    stateFlow.emit(3)
    // endregion

    delay(50)
    collector.cancelAndJoin()
    lateCollector.cancelAndJoin()
    println("Done")
}