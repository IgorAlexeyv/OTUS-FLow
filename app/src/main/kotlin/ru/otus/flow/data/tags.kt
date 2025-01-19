package ru.otus.flow.data

import kotlinx.coroutines.delay

/**
 * Tag data
 */
data class Tag(
    val id: Int,
    val name: String
)

/**
 * Returns tags for user
 */
suspend fun getTagsForUser(userId: Int): Result<List<Tag>> {
    delay(NETWORK_DELAY)
    return Result.success(tags[userId] ?: emptyList())
}

internal val tags = mapOf(
    users[0].id to listOf(
        Tag(1, "Tag1"),
        Tag(2, "Tag2")
    ),
    users[1].id to listOf(
        Tag(3, "Tag3"),
        Tag(4, "Tag4")
    ),
    users[2].id to listOf(
        Tag(5, "Tag5"),
        Tag(6, "Tag6")
    ),
    users[3].id to listOf(
        Tag(7, "Tag7"),
        Tag(8, "Tag8")
    )
)