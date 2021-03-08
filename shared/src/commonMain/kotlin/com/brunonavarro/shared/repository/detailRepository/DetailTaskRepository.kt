package com.brunonavarro.shared.repository.detailRepository

import com.brunonavarro.shared.AppDatabaseQueries
import com.brunonavarro.shared.model.Comment
import com.brunonavarro.shared.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DetailTaskRepository(
    private val sqlDelight: AppDatabaseQueries
): DetailTaskInterface {

    override suspend fun getTask(id: Int): Task = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val query = sqlDelight.selectTaskId(id.toLong()).executeAsOne()
            val task = Task()
            task.id = query.id.toInt()
            task.title = query.title
            task.body = query.body
            task.isComplete = query.isComplete?: false
            task.createDate = query.createDate
            task.finishDate = query.finishDate
            continuation.resume(task)
        }
    }

    override suspend fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.updateTaskId(task.id!!.toLong(),task.title.toString(), task.body.toString(),
                    task.isComplete, task.createDate.toString(), task.finishDate.toString())
        }
    }

    override suspend fun removeTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.deleteTask(task.id!!.toLong())
        }
    }

    override suspend fun getComments(taskId: Int): List<Comment> = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val list = sqlDelight.selectAllComment().executeAsList()
                .map {
                    Comment(id = it.id.toInt(), message = it.message)
                }
            continuation.resume(list)
        }
    }

    override suspend fun addComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.insertCommentItem(comment.taskId!!.toLong(),
                comment.message!!.toString())
        }
    }

    override suspend fun removeComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.deleteComment(comment.id!!.toLong())
        }
    }

    override suspend fun updateComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.updateCommentId(comment.message.toString(), comment.id!!.toLong())
        }
    }

}