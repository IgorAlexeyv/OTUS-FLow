package ru.otus.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import ru.otus.flow.data.Tag
import ru.otus.flow.data.User
import ru.otus.flow.data.getTagsForUser
import ru.otus.flow.data.getUsers
import ru.otus.flow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            groupUsers.setOnCheckedStateChangeListener { _, ids ->
                val chipId = ids.firstOrNull()
                if (null == chipId) {
                    selectUser(null)
                    return@setOnCheckedStateChangeListener
                }
                val userId = groupUsers.findViewById<Chip>(chipId).tag as Int
                selectUser(userId)
            }
            groupTags.setOnCheckedStateChangeListener { _, ids ->
                val tags = ids
                    .map { id -> groupTags.findViewById<Chip>(id).tag as Int }
                    .toSet()
                selectTags(tags)
            }
        }

        loadUsers()
    }

    private fun populateUsers(users: List<User>) {
        val group = binding.groupUsers
        group.removeAllViews()
        users.forEach {
            val chip = Chip(group.context).apply {
                text = it.name
                tag = it.id
                isClickable = true
                isCheckable = true
                isFocusable = true
            }
            group.addView(chip)
        }
    }

    private fun populateTags(tags: List<Tag>) {
        val group = binding.groupTags
        group.removeAllViews()
        tags.forEach {
            val chip = Chip(group.context).apply {
                text = it.name
                tag = it.id
                isClickable = true
                isCheckable = true
                isFocusable = true
            }
            group.addView(chip)
        }
    }

    private fun hideTags() {
        binding.controlsTags.visibility = android.view.View.GONE
    }

    private fun showTags() {
        binding.controlsTags.visibility = android.view.View.VISIBLE
    }

    // region Logic

    private fun loadUsers() {
        Log.i(TAG, "Loading users")
        lifecycleScope.launch {
            val result = getUsers()
            if (result.isSuccess) {
                populateUsers(result.getOrThrow())
            } else {
                Log.e(TAG, "Failed to load users", result.exceptionOrNull())
            }
        }
    }

    private fun selectUser(userId: Int?) {
        Log.i(TAG, "Selected user: $userId")
        loadTags(userId)
    }

    private fun loadTags(userId: Int?) {
        Log.i(TAG, "Loading tags for user: $userId")
        hideTags()
        if (null == userId) {
            populateTags(emptyList())
            return
        }

        lifecycleScope.launch {
            val result = getTagsForUser(userId)
            if (result.isSuccess) {
                populateTags(result.getOrThrow())
                showTags()
            } else {
                Log.e(TAG, "Failed to load tags", result.exceptionOrNull())
            }
        }
    }

    private fun selectTags(tags: Set<Int>) {
        Log.i(TAG, "Selected tags: $tags")
    }

    // endregion


    companion object {
        private const val TAG = "MainActivity"
    }
}