package com.brunonavarro.smiledu.ui.main

interface MainListener {
    fun showProgressBar(isShow: Boolean)
    fun createTaskSuccess()
    fun errorMessage(codeError: Int?, message: String?)
}