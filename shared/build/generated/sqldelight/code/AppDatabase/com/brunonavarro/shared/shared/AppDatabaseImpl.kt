package com.brunonavarro.shared.shared

import com.brunonavarro.shared.AppDatabase
import com.brunonavarro.shared.AppDatabaseQueries
import com.brunonavarro.shared.Comment
import com.brunonavarro.shared.Task
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.jvm.JvmField
import kotlin.reflect.KClass

internal val KClass<AppDatabase>.schema: SqlDriver.Schema
  get() = AppDatabaseImpl.Schema

internal fun KClass<AppDatabase>.newInstance(driver: SqlDriver): AppDatabase =
    AppDatabaseImpl(driver)

private class AppDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), AppDatabase {
  override val appDatabaseQueries: AppDatabaseQueriesImpl = AppDatabaseQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE task (
          |  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |  title TEXT NOT NULL,
          |  body TEXT NOT NULL,
          |  isComplete INTEGER NOT NULL,
          |  createDate TEXT NOT NULL,
          |  finishDate TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE comment (
          |  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |  taskId INTEGER NOT NULL,
          |  message TEXT NOT NULL,
          |  FOREIGN KEY (taskId) REFERENCES task(id)
          |)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class AppDatabaseQueriesImpl(
  private val database: AppDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), AppDatabaseQueries {
  internal val selectAllTask: MutableList<Query<*>> = copyOnWriteList()

  internal val selectTaskId: MutableList<Query<*>> = copyOnWriteList()

  internal val selectAllComment: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectAllTask(mapper: (
    id: Int,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) -> T): Query<T> = Query(-47640865, selectAllTask, driver, "AppDatabase.sq", "selectAllTask",
      "SELECT * FROM task ORDER BY id DESC") { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!! == 1L,
      cursor.getString(4)!!,
      cursor.getString(5)!!
    )
  }

  override fun selectAllTask(): Query<Task> = selectAllTask(::Task)

  override fun <T : Any> selectTaskId(id: Int, mapper: (
    id: Int,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) -> T): Query<T> = SelectTaskIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!! == 1L,
      cursor.getString(4)!!,
      cursor.getString(5)!!
    )
  }

  override fun selectTaskId(id: Int): Query<Task> = selectTaskId(id, ::Task)

  override fun <T : Any> selectAllComment(mapper: (
    id: Int,
    taskId: Long,
    message: String
  ) -> T): Query<T> = Query(557932229, selectAllComment, driver, "AppDatabase.sq",
      "selectAllComment", "SELECT * FROM comment ORDER BY id DESC") { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!,
      cursor.getString(2)!!
    )
  }

  override fun selectAllComment(): Query<Comment> = selectAllComment(::Comment)

  override fun insertTaskItem(
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) {
    driver.execute(990919164, """
    |INSERT OR REPLACE INTO task (title, body, isComplete, createDate, finishDate)
    |VALUES (?, ?, ?, ?, ?)
    """.trimMargin(), 5) {
      bindString(1, title)
      bindString(2, body)
      bindLong(3, if (isComplete) 1L else 0L)
      bindString(4, createDate)
      bindString(5, finishDate)
    }
    notifyQueries(990919164, {database.appDatabaseQueries.selectAllTask +
        database.appDatabaseQueries.selectTaskId})
  }

  override fun updateTaskId(
    id: Int?,
    title: String,
    body: String,
    isComplete: Boolean,
    createDate: String,
    finishDate: String
  ) {
    driver.execute(-786031532, """
    |INSERT OR REPLACE INTO task (id, title, body, isComplete, createDate, finishDate)
    |VALUES (?, ?, ?, ?, ?, ?)
    """.trimMargin(), 6) {
      bindLong(1, id?.let { it.toLong() })
      bindString(2, title)
      bindString(3, body)
      bindLong(4, if (isComplete) 1L else 0L)
      bindString(5, createDate)
      bindString(6, finishDate)
    }
    notifyQueries(-786031532, {database.appDatabaseQueries.selectAllTask +
        database.appDatabaseQueries.selectTaskId})
  }

  override fun updateIsCompleteTaskId(isComplete: Boolean, id: Int) {
    driver.execute(474855863, """UPDATE task SET isComplete = ? WHERE id = ?""", 2) {
      bindLong(1, if (isComplete) 1L else 0L)
      bindLong(2, id.toLong())
    }
    notifyQueries(474855863, {database.appDatabaseQueries.selectAllTask +
        database.appDatabaseQueries.selectTaskId})
  }

  override fun deleteTask(id: Int) {
    driver.execute(1751322939, """DELETE FROM task WHERE id = ?""", 1) {
      bindLong(1, id.toLong())
    }
    notifyQueries(1751322939, {database.appDatabaseQueries.selectAllTask +
        database.appDatabaseQueries.selectTaskId})
  }

  override fun insertCommentItem(taskId: Long, message: String) {
    driver.execute(-738332722, """
    |INSERT OR REPLACE INTO comment (taskId, message)
    |VALUES (?, ?)
    """.trimMargin(), 2) {
      bindLong(1, taskId)
      bindString(2, message)
    }
    notifyQueries(-738332722, {database.appDatabaseQueries.selectAllComment})
  }

  override fun updateCommentId(message: String, id: Int) {
    driver.execute(2136264838, """UPDATE comment SET message = ? WHERE id = ?""", 2) {
      bindString(1, message)
      bindLong(2, id.toLong())
    }
    notifyQueries(2136264838, {database.appDatabaseQueries.selectAllComment})
  }

  override fun deleteComment(id: Int) {
    driver.execute(886697705, """DELETE FROM comment WHERE id = ?""", 1) {
      bindLong(1, id.toLong())
    }
    notifyQueries(886697705, {database.appDatabaseQueries.selectAllComment})
  }

  private inner class SelectTaskIdQuery<out T : Any>(
    @JvmField
    val id: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectTaskId, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-2099911097,
        """SELECT * FROM task WHERE id = ?""", 1) {
      bindLong(1, id.toLong())
    }

    override fun toString(): String = "AppDatabase.sq:selectTaskId"
  }
}
