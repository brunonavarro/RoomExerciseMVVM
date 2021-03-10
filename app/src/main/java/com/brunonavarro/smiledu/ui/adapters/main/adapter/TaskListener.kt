package com.brunonavarro.smiledu.ui.adapters.main.adapter

import com.brunonavarro.shared.model.Task

interface TaskListener {
    fun onIsCompleteTask(task: Task, isComplete: Boolean)
}