package ru.otus.flow

fun main() {
    collection().forEach { value -> println(value) }
}

fun collection(): List<Int> = listOf(1, 2, 3)
