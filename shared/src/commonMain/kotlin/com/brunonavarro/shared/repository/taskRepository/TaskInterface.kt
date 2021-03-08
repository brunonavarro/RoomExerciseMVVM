package com.brunonavarro.shared.repository.taskRepository

import com.brunonavarro.shared.model.Task

interface TaskInterface {
    suspend fun getTasks(): List<Task>
    suspend fun updateTask(task: Task)
    suspend fun updateIsComplete(task: Task)
    suspend fun addTask(task: Task)
}