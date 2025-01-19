package ru.otus.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import ru.otus.flow.data.User
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
    }

    // endregion


    companion object {
        private const val TAG = "MainActivity"
    }
}