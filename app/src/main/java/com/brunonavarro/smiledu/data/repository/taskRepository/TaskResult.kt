package com.brunonavarro.smiledu.data.repository.taskRepository

import com.brunonavarro.smiledu.data.entity.Task

data class TaskResult(
    var success: Boolean = false,
    var taskList: MutableList<Task>? = null,
    var task: Task? = null
)