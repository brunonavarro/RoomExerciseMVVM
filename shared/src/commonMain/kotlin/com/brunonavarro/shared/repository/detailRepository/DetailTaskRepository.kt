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

    override suspend fun getMaxComment(): Comment = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val query = sqlDelight.selectMaxItemComment().executeAsOne()
            val comment = Comment()
            comment.id = query.id
            comment.taskId = query.taskId
            comment.message = query.message
            continuation.resume(comment)
        }
    }

    override suspend fun getTask(id: Int): Task = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val query = sqlDelight.selectTaskId(id).executeAsOne()
            val task = Task()
            task.id = query.id
            task.title = query.title
            task.body = query.body
            task.isComplete = query.isComplete
            task.createDate = query.createDate
            task.finishDate = query.finishDate
            continuation.resume(task)
        }
    }

    override suspend fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.updateTaskId(task.id!!,task.title.toString(), task.body.toString(),
                    task.isComplete!!, task.createDate.toString(), task.finishDate.toString())
        }
    }

    override suspend fun removeTask(task: Task) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.deleteTask(task.id!!)
        }
    }

    override suspend fun getComments(taskId: Int): List<Comment> = suspendCoroutine{ continuation->
        CoroutineScope(Dispatchers.Unconfined).launch {
            val list = sqlDelight.selectAllComment(taskId).executeAsList()
                .map {
                    Comment(id = it.id, taskId = it.taskId, message = it.message)
                }
            continuation.resume(list)
        }
    }

    override suspend fun addComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.insertCommentItem(comment.taskId!!,
                comment.message!!.toString())
        }
    }

    override suspend fun removeComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.deleteComment(comment.id!!)
        }
    }

    override suspend fun updateComment(comment: Comment) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            sqlDelight.updateCommentId(comment.message.toString(), comment.id!!)
        }
    }
}