package ru.otus.flow.data

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
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
suspend fun getNotesForUser(userId: Int?, tags: Set<Int>): Result<List<Note>> {
    if (null == userId) {
        return Result.success(emptyList())
    }
    delay(NETWORK_DELAY)
    return Result.success(notes[userId]?.filter { notes -> notes.tags.any { tags.contains(it) } } ?: emptyList())
}

fun getNotesFlow(userId: Int?, tags: Set<Int>): Flow<List<Note>> = flow {
    val notes = getNotesForUser(userId, tags).getOrThrow()
    if (notes.isEmpty()) {
        emit(emptyList())
        return@flow
    }

    while(currentCoroutineContext().isActive) {
        val notesFeed = mutableListOf<Note>()
        for (note in notes) {
            notesFeed.add(note)
            emit(notesFeed.toList())
            delay(FEED_DELAY)
        }
        delay(REFRESH_DELAY)
    }
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