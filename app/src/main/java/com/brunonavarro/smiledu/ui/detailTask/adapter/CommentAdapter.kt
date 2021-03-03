package com.brunonavarro.smiledu.ui.detailTask.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ItemCommentBinding
import com.brunonavarro.smiledu.databinding.ItemTaskBinding

class CommentAdapter(
    val listener: CommentListener,
    val activity: Activity
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),listener, activity
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        val itemHolder = holder as CommentHolder
        itemHolder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}