package com.brunonavarro.smiledu.ui.detailTask.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.databinding.ItemCommentBinding

class CommentHolder(
    val binding: ItemCommentBinding
) : RecyclerView.ViewHolder(binding.root){

    var context: Context? = null

    fun bind(data: Comment){
        context = binding.root.context
        binding.setComment(data)

    }
}