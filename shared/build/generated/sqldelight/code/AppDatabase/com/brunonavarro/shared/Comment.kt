package com.brunonavarro.shared

import kotlin.Long
import kotlin.String

data class Comment(
  val id: Long,
  val taskId: Long,
  val message: String
) {
  override fun toString(): String = """
  |Comment [
  |  id: $id
  |  taskId: $taskId
  |  message: $message
  |]
  """.trimMargin()
}
