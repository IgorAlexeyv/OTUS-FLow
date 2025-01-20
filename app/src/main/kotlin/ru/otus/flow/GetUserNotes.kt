package ru.otus.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import ru.otus.flow.data.Note
import ru.otus.flow.data.getNotesFlow

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserNotes(userId: Int?) {

    /**
     * Keeps tags to filter notes
     */
    private val tagsFlow = MutableStateFlow(emptySet<Int>())

    fun setTags(tags: Set<Int>) {
        tagsFlow.value = tags
    }

    val state: Flow<List<Note>> = if(null == userId) {
        flowOf(emptyList())
    } else {
        // For each tag change get notes
        tagsFlow.flatMapLatest { tags ->
            getNotesFlow(userId, tags)
        }
    }
}