package ru.otus.flow

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()

    val collector = launch {
        sharedFlow.collect { value -> println("Collected: $value") }
    }

    val lateCollector = launch {
        delay(100)
        sharedFlow.collect { value -> println("Late collected: $value") }
    }

    // region Emit values
    delay(50)
    sharedFlow.emit(1)
    delay(50)
    sharedFlow.emit(2)
    delay(50)
    sharedFlow.emit(3)
    // endregion

    collector.cancelAndJoin()
    lateCollector.cancelAndJoin()
    println("Done")
}