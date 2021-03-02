package com.brunonavarro.smiledu.data.repository.taskRepository

import com.brunonavarro.smiledu.data.entity.Task

interface TaskInterface {
    suspend fun getTasks(): TaskResult
    suspend fun getTask(id: Int): TaskResult
    suspend fun updateTask(task: Task): TaskResult
    suspend fun removeTask(task: Task): TaskResult
    suspend fun addTask(task: Task): TaskResult
}