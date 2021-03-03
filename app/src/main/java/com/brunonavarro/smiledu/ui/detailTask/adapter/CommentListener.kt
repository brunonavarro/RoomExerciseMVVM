package com.brunonavarro.smiledu.ui.detailTask.adapter

import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task

interface CommentListener {
    fun onClickEdit(comment: Comment)
    fun onClickDelete(comment: Comment)
}