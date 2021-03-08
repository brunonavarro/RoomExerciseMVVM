package com.brunonavarro.shared

import kotlin.Long
import kotlin.String

data class Task(
  val id: Long,
  val title: String,
  val body: String,
  val isComplete: Long?,
  val createDate: String,
  val finishDate: String
) {
  override fun toString(): String = """
  |Task [
  |  id: $id
  |  title: $title
  |  body: $body
  |  isComplete: $isComplete
  |  createDate: $createDate
  |  finishDate: $finishDate
  |]
  """.trimMargin()
}
