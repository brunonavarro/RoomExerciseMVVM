package com.brunonavarro.shared.repository.taskRepository

import com.brunonavarro.shared.AppDatabase
import com.brunonavarro.shared.AppDatabaseQueries
import com.brunonavarro.shared.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal expect fun cache(): AppDatabase

class TaskRepository(
    private val sqlDelight: AppDatabaseQueries = cache().appDatabaseQueries
): TaskInterface {

    override suspend fun addTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.insertTaskItem(task.title.toString(),
                task.body.toString(), task.isComplete, task.createDate.toString(),
                task.finishDate.toString())
        }
    }

    override suspend fun getTasks(): List<Task> = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val list = sqlDelight.selectAllTask()
                .executeAsList()
                .map {
                    Task(id = it.id.toInt(), title = it.title,
                        body = it.body, createDate = it.createDate,
                        finishDate = it.finishDate)
                }

            continuation.resume(list)
        }
    }

    override suspend fun updateTask(task: Task) {
        sqlDelight.updateTaskId(task.id!!.toLong(), task.title.toString(),
            task.body.toString(), task.isComplete, task.createDate.toString(),
            task.finishDate.toString())
    }
}