package ru.otus.flow

fun main() {
    sequence().forEach { value -> println(value) }
}

fun sequence(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(100) // Long operation, BLOCKS the thread!
        yield(i) // next value
    }
}

