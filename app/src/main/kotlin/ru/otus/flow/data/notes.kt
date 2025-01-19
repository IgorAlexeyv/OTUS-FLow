package ru.otus.flow.data

import kotlinx.coroutines.delay

data class Note(
    val id: Int,
    val userId: Int,
    val tags: Set<Int>,
    val text: String
)

/**
 * Returns notes for user
 */
suspend fun getNotesForUser(userId: Int, tags: Set<Int>): Result<List<Note>> {
    delay(NETWORK_DELAY)
    return Result.success(notes[userId]?.filter { notes -> notes.tags.any { tags.contains(it) } } ?: emptyList())
}

internal val notes = mapOf(
    users[0].id to listOf(
        Note(1, users[0].id, tags[users[0].id]?.get(0)?.id?.let(::setOf).orEmpty(), "Note1"),
        Note(2, users[0].id, tags[users[0].id]?.get(1)?.id?.let(::setOf).orEmpty(), "Note2")
    ),
    users[1].id to listOf(
        Note(3, users[1].id, tags[users[1].id]?.get(0)?.id?.let(::setOf).orEmpty(), "Note3"),
        Note(4, users[1].id, tags[users[1].id]?.get(1)?.id?.let(::setOf).orEmpty(), "Note4")
    ),
    users[2].id to listOf(
        Note(5, users[2].id, tags[users[2].id]?.get(0)?.id?.let(::setOf).orEmpty(), "Note5"),
        Note(6, users[2].id, tags[users[2].id]?.get(1)?.id?.let(::setOf).orEmpty(), "Note6")
    ),
    users[3].id to listOf(
        Note(7, users[3].id, tags[users[3].id]?.get(0)?.id?.let(::setOf).orEmpty(), "Note7"),
        Note(8, users[3].id, tags[users[3].id]?.get(1)?.id?.let(::setOf).orEmpty(), "Note8")
    )
)