package com.brunonavarro.shared

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String

interface AppDatabaseQueries : Transacter {
  fun <T : Any> selectAllTask(mapper: (
    id: Long,
    title: String,
    body: String,
    isComplete: Long?,
    createDate: String,
    finishDate: String
  ) -> T): Query<T>

  fun selectAllTask(): Query<Task>

  fun <T : Any> selectTaskId(id: Long, mapper: (
    id: Long,
    title: String,
    body: String,
    isComplete: Long?,
    createDate: String,
    finishDate: String
  ) -> T): Query<T>

  fun selectTaskId(id: Long): Query<Task>

  fun <T : Any> selectAllComment(mapper: (
    id: Long,
    taskId: Long,
    message: String
  ) -> T): Query<T>

  fun selectAllComment(): Query<Comment>

  fun insertTaskItem(
    title: String,
    body: String,
    isComplete: Long?,
    createDate: String,
    finishDate: String
  )

  fun updateTaskId(
    id: Long?,
    title: String,
    body: String,
    isComplete: Long?,
    createDate: String,
    finishDate: String
  )

  fun updateIsCompleteTaskId(isComplete: Long?, id: Long)

  fun deleteTask(id: Long)

  fun insertCommentItem(taskId: Long, message: String)

  fun updateCommentId(message: String, id: Long)

  fun deleteComment(id: Long)
}
