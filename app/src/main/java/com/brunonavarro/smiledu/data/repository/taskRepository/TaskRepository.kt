package com.brunonavarro.smiledu.data.repository.taskRepository

import com.brunonavarro.smiledu.data.database.AppDatabase
import com.brunonavarro.smiledu.data.entity.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskRepository(
    private val appDatabase: AppDatabase
): TaskInterface {

    override suspend fun addTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().insertTask(task)
        }
    }

    override suspend fun getTasks(): List<Task> = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            val list = appDatabase.taskDao().getTasks()
            continuation.resume(list)
        }
    }

    override suspend fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().updateTask(task)
        }
    }
}