package com.brunonavarro.smiledu.ui.main.adapter

import com.brunonavarro.smiledu.data.entity.Task

interface TaskListener {
    fun onIsCompleteTask(task: Task, isComplete: Boolean)
}