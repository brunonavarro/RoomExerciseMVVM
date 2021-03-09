package com.brunonavarro.smiledu.ui.main

import com.brunonavarro.smiledu.data.entity.Task

interface MainListener {
    fun showProgressBar(isShow: Boolean)
    fun createTaskSuccess(task: Task)
    fun errorMessage(codeError: Int?, message: String?)
}