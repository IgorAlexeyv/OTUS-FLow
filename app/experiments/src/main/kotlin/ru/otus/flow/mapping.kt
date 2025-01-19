package ru.otus.flow

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    // 2, 4, 6
    val mapped = flowOf(1, 2, 3).map { it * 2 }
    // 2, 4
    val filtered = flowOf(1, 2, 3, 4).filter { it % 2 == 0 }
    // 2, 4, 6
    val mappedNotNull = flowOf(1, 2, 3, null).mapNotNull { it?.times(2) }

    merge(mapped, filtered, mappedNotNull).collect { value -> println(value) }
}