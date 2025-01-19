package ru.otus.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
fun main(): Unit = runBlocking {
    val flow: Flow<Int> = flow {
        emit(1)
        delay(45)
        emit(2)
        delay(25)
        emit(3)
    }

    val mapped: Flow<String> = flow.flatMapLatest { fromUpstream ->
        flow {
            repeat(3) { count ->
                delay(10)
                emit("Transformed $fromUpstream - ${count + 1}")
            }
        }
    }

    mapped.collect{ value -> println(value) }
}