package com.brunonavarro.shared.repository.detailRepository

import com.brunonavarro.shared.model.Comment
import com.brunonavarro.shared.model.Task

interface DetailTaskInterface {
    suspend fun getTask(id: Int): Task
    suspend fun updateTask(task: Task)
    suspend fun removeTask(task: Task)
    suspend fun addComment(comment: Comment)
    suspend fun getComments(taskId: Int): List<Comment>
    suspend fun removeComment(comment: Comment)
    suspend fun updateComment(comment: Comment)
}