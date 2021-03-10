package com.brunonavarro.shared.viewModel.detailTask

import com.brunonavarro.shared.model.Comment

interface DetailTaskListener {
    fun showProgressBar(isShow: Boolean)
    fun messageSuccess(codeMessage: Int, comment: Comment?)
    fun errorMessage(codeError: Int?, message: String?)
}