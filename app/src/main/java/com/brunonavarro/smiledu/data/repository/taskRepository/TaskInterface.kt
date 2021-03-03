package com.brunonavarro.smiledu.data.repository.taskRepository

import com.brunonavarro.smiledu.data.entity.Task

interface TaskInterface {
    suspend fun getTasks(): List<Task>
    suspend fun updateTask(task: Task)
    suspend fun addTask(task: Task)
}