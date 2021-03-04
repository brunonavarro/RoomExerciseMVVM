package com.brunonavarro.smiledu.ui.detailTask.adapter

import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task

interface CommentListener {
    fun onClickEdit(comment: Comment, isEdit: Boolean)
    fun onClickDelete(comment: Comment, position: Int)
}