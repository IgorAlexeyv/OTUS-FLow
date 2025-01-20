package ru.otus.flow.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

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

fun getNotesFlow(userId: Int, tags: Set<Int>): Flow<List<Note>> = flow {
    emit(emptyList())
    emit(getNotesForUser(userId, tags).getOrThrow())
}

internal val notes: Map<Int, List<Note>> = users.associate {
    it.id to createNotesForUser(it.id)
}

private fun createNotesForUser(userId: Int): List<Note> {
    val tagsForUser = tags[userId] ?: emptyList()
    return (1 .. Random.nextInt(MIN_NOTES, MAX_NOTES)).map {
        Note(
            it,
            userId,
            (1..tagsForUser.lastIndex).map { tagsForUser.random().id }.toSet(),
            "Note$it"
        )
    }
}