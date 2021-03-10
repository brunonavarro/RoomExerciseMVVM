package com.brunonavarro.smiledu.ui.adapters.detailTask.adapter

import com.brunonavarro.shared.model.Comment


interface CommentListener {
    fun onClickEdit(comment: Comment, isEdit: Boolean, position: Int)
    fun onClickDelete(comment: Comment, position: Int)
}