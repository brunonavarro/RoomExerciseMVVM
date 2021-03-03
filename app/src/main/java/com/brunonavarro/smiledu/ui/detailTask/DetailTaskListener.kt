package com.brunonavarro.smiledu.ui.detailTask

interface DetailTaskListener {
    fun showProgressBar(isShow: Boolean)
    fun createTaskSuccess()
    fun errorMessage(codeError: Int?, message: String?)
}