package com.brunonavarro.shared

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String

interface AppDatabaseQueries : Transacter {
  fun <T : Any> selectAllTask(mapper: (
    id: Int,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) -> T): Query<T>

  fun selectAllTask(): Query<Task>

  fun <T : Any> selectTaskId(id: Int, mapper: (
    id: Int,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) -> T): Query<T>

  fun selectTaskId(id: Int): Query<Task>

  fun <T : Any> selectMaxItemTask(mapper: (
    id: Int,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) -> T): Query<T>

  fun selectMaxItemTask(): Query<Task>

  fun <T : Any> selectAllComment(taskId: Int, mapper: (
    id: Int,
    taskId: Int,
    message: String
  ) -> T): Query<T>

  fun selectAllComment(taskId: Int): Query<Comment>

  fun <T : Any> selectMaxItemComment(mapper: (
    id: Int,
    taskId: Int,
    message: String
  ) -> T): Query<T>

  fun selectMaxItemComment(): Query<Comment>

  fun insertTaskItem(
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  )

  fun updateTaskId(
    id: Int?,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  )

  fun updateIsCompleteTaskId(isComplete: Boolean, id: Int)

  fun deleteTask(id: Int)

  fun insertCommentItem(taskId: Int, message: String)

  fun updateCommentId(message: String, id: Int)

  fun deleteComment(id: Int)
}
