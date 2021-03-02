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

    override suspend fun addTask(task: Task): TaskResult = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().insertTask(task)
            continuation.resume(TaskResult(true))
        }
    }

    override suspend fun getTasks(): TaskResult = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            val list = appDatabase.taskDao().getTasks()
            continuation.resume(TaskResult(true, list.toMutableList()))
        }
    }

    override suspend fun getTask(id: Int): TaskResult = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            val task = appDatabase.taskDao().getTask(id)
            continuation.resume(TaskResult(true, null, task))
        }
    }

    override suspend fun updateTask(task: Task): TaskResult = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().updateTask(task)
            continuation.resume(TaskResult(true))
        }
    }

    override suspend fun removeTask(task: Task): TaskResult  = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().deleteTask(task)
            continuation.resume(TaskResult(true))
        }
    }

}