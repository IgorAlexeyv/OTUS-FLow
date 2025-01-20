package ru.otus.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.otus.flow.data.Note
import ru.otus.flow.data.Tag
import ru.otus.flow.data.User
import ru.otus.flow.data.getUsers
import ru.otus.flow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter

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
            adapter = NotesAdapter()
            groupNotes.adapter = adapter
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

    private fun populateNotes(notes: List<Note>) {
        Log.i(TAG, "Populating notes: $notes")
        adapter.submitList(notes)
    }

    private fun hideTags() {
        binding.controlsTags.visibility = android.view.View.GONE
    }

    private fun showTags() {
        binding.controlsTags.visibility = android.view.View.VISIBLE
    }

    // region Logic

    private val getUserContent = GetUserContent()

    private fun loadUsers() {
        Log.i(TAG, "Loading users")
        lifecycleScope.launch {
            val result = getUsers()
            if (result.isSuccess) {
                populateUsers(result.getOrThrow())
                getUserContent.setUser(null)
                subscribeUserContent()
            } else {
                Log.e(TAG, "Failed to load users", result.exceptionOrNull())
            }
        }
    }

    private fun selectUser(userId: Int?) {
        Log.i(TAG, "Selected user: $userId")
        getUserContent.setUser(userId)
    }

    private fun subscribeUserContent() {
        getUserContent.state.onEach { content ->
            populateTags(content.tags.toList())
            populateNotes(content.notes)
            if (content.tags.isEmpty()) {
                hideTags()
            } else {
                showTags()
            }
        }.launchIn(lifecycleScope)
    }

    private fun selectTags(tags: Set<Int>) {
        Log.i(TAG, "Selected tags: $tags")
        getUserContent.setTags(tags)
    }

    // endregion

    companion object {
        private const val TAG = "MainActivity"
    }
}
