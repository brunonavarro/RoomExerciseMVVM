package com.brunonavarro.smiledu.data.repository.detailTaskRepository

import com.brunonavarro.smiledu.data.database.AppDatabase
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DetailTaskRepository(
    private val appDatabase: AppDatabase
): DetailTaskInterface {

    override suspend fun getTask(id: Int): Task = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            val task = appDatabase.taskDao().getTask(id)
            continuation.resume(task)
        }
    }

    override suspend fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().updateTask(task)
        }
    }

    override suspend fun removeTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.taskDao().deleteTask(task)
        }
    }

    override suspend fun getComments(taskId: Int): List<Comment> = suspendCoroutine{continuation->
        CoroutineScope(Dispatchers.IO).launch {
            val comments = appDatabase.commentDao().getComments(taskId)
            continuation.resume(comments)
        }
    }

    override suspend fun addComment(comment: Comment) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.commentDao().insertComment(comment)
        }
    }

}