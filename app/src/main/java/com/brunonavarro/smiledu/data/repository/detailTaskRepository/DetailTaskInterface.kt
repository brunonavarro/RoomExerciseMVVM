package com.brunonavarro.smiledu.data.repository.detailTaskRepository

import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task

interface DetailTaskInterface {
    suspend fun getTask(id: Int): Task
    suspend fun updateTask(task: Task)
    suspend fun removeTask(task: Task)
    suspend fun addComment(comment: Comment)
    suspend fun getComments(taskId: Int): List<Comment>
}