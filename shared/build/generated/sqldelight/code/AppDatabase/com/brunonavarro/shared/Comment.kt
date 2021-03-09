package com.brunonavarro.shared

import kotlin.Int
import kotlin.String

data class Comment(
  val id: Int,
  val taskId: Int,
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
