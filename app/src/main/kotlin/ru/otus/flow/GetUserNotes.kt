package ru.otus.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.otus.flow.data.Note
import ru.otus.flow.data.getNotesFlow

class GetUserNotes(userId: Int?, tags: Set<Int>) {
    val state: Flow<List<Note>> = if(null == userId) {
        flowOf(emptyList())
    } else {
        getNotesFlow(userId, tags)
    }
}