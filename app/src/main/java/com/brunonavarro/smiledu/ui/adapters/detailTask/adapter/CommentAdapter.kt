package com.brunonavarro.smiledu.ui.adapters.detailTask.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.shared.model.Comment
import com.brunonavarro.smiledu.databinding.ItemCommentBinding
import com.brunonavarro.smiledu.ui.adapters.detailTask.holder.CommentHolder

class CommentAdapter(
        val listener: CommentListener,
        val activity: Activity
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList = mutableListOf<Comment>()

    fun addItem(position: Int, comment: Comment){
        itemList.add(position, comment)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, comment: Comment){
        itemList.remove(comment)
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int, comment: Comment){
        itemList.forEach {
            if (it.id == comment.id){
                it.id = comment.id
                it.taskId = comment.taskId
                it.message = comment.message
            }
        }
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentHolder(
                ItemCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ), listener, activity
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        val itemHolder = holder as CommentHolder
        itemHolder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}