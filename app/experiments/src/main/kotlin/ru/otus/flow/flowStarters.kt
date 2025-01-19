package ru.otus.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // From iterable
    val fromIterable: Flow<Int> = listOf(1, 2, 3).asFlow()
    // From sequence
    val fromSequence: Flow<Int> = sequence { for (i in 1..3) yield(i) }.asFlow()
    // From range
    val fromRange: Flow<Int> = (1..3).asFlow()
    // From values
    val fromValues: Flow<Int> = flowOf(1, 2, 3)
    // Empty flow
    val emptyFlow: Flow<Int> = emptyFlow()

    merge(fromIterable, fromSequence, fromRange, fromValues, emptyFlow).collect { value -> println(value) }
}