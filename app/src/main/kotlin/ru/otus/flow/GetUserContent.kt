package ru.otus.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import ru.otus.flow.data.Note
import ru.otus.flow.data.Tag
import ru.otus.flow.data.getNotesFlow
import ru.otus.flow.data.getTagsFlow

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserContent {
    /**
     * Keeps tags to filter notes
     */
    private val userFlow = MutableStateFlow<Int?>(null)

    /**
     * Sets user to get content for
     */
    fun setUser(userId: Int?) {
        userFlow.value = userId
    }

    /**
     * Keeps tags to filter notes
     */
    private val tagsFlow = MutableStateFlow(emptySet<Int>())

    /**
     * Sets tags to filter notes
     */
    fun setTags(tags: Set<Int>) {
        tagsFlow.value = tags
    }

    val state: Flow<UserContent> = userFlow.transformLatest { user ->
        emit(UserContent(user, emptyList(), emptyList()))

        getTagsFlow(user).collectLatest { tags ->
            emit(UserContent(user, tags.map { it to false }, emptyList()))

            tagsFlow.onStart { emit(emptySet()) }.collectLatest { selectedTags ->
                emitAll(
                    getNotesFlow(user, selectedTags).map {
                        UserContent(
                            user,
                            tags.map { tag -> tag to selectedTags.contains(tag.id) },
                            it
                        )
                    }
                )
            }
        }
    }
}

data class UserContent(
    val userId: Int?,
    val tags: List<Pair<Tag, Boolean>>,
    val notes: List<Note>
)