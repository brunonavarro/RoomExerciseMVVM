package com.brunonavarro.shared.repository.taskRepository

import com.brunonavarro.shared.AppDatabaseQueries
import com.brunonavarro.shared.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskRepository(
    private val sqlDelight: AppDatabaseQueries
): TaskInterface {

    override suspend fun addTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.insertTaskItem(task.title.toString(),
                task.body.toString(), task.isComplete?: false, task.createDate.toString(),
                task.finishDate.toString())
        }
    }

    override suspend fun getTasks(): List<Task> = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val list = sqlDelight.selectAllTask()
                .executeAsList()
                .map {
                    Task(id = it.id, title = it.title,
                        body = it.body, isComplete = it.isComplete ,createDate = it.createDate,
                        finishDate = it.finishDate)
                }

            continuation.resume(list)
        }
    }

    override suspend fun updateTask(task: Task) {
        sqlDelight.updateTaskId(
            task.id!!,
            task.title.toString(),
            task.body.toString(),
            task.isComplete ?: false,
            task.createDate.toString(),
            task.finishDate.toString())
    }

    override suspend fun updateIsComplete(task: Task) {
        sqlDelight.updateIsCompleteTaskId(
            task.isComplete ?: false,
            task.id!!)
    }
}