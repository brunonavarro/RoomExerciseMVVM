package com.brunonavarro.smiledu.ui.adapters.detailTask.adapter

import com.brunonavarro.smiledu.data.entity.Comment

interface CommentListener {
    fun onClickEdit(comment: Comment, isEdit: Boolean)
    fun onClickDelete(comment: Comment, position: Int)
}