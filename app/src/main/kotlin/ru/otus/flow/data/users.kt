package ru.otus.flow.data

import kotlinx.coroutines.delay

/**
 * User data
 */
data class User(
    val id: Int,
    val name: String
)

/**
 * Returns user list
 */
suspend fun getUsers(): Result<List<User>> {
    delay(NETWORK_DELAY)
    return Result.success(users)
}

internal val users = listOf(
    User(1, "Alice"),
    User(2, "Bob"),
    User(3, "Charlie"),
    User(4, "David")
)
