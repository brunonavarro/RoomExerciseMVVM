package com.brunonavarro.shared

import kotlin.Boolean
import kotlin.Int
import kotlin.String

data class Task(
  val id: Int,
  val title: String,
  val body: String,
  val isComplete: Boolean,
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
