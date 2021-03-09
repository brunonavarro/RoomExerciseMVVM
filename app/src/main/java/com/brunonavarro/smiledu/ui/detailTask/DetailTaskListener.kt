package com.brunonavarro.smiledu.ui.detailTask

import com.brunonavarro.smiledu.data.entity.Comment

interface DetailTaskListener {
    fun showProgressBar(isShow: Boolean)
    fun messageSuccess(codeMessage: Int, comment: Comment?)
    fun errorMessage(codeError: Int?, message: String?)
}