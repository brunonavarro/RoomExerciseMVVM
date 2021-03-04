package com.brunonavarro.smiledu.ui.detailTask.adapter

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.R
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.databinding.ItemCommentBinding

class CommentHolder(
    val binding: ItemCommentBinding,
    val listener: CommentListener,
    val activity: Activity
) : RecyclerView.ViewHolder(binding.root){

    var context: Context? = null

    fun bind(data: Comment){
        context = binding.root.context
        binding.setComment(data)

        binding.moreOptions.setOnClickListener {
            val popup = PopupMenu(activity, binding.moreOptions, Gravity.START)

            popup.menuInflater.inflate(R.menu.menu_comment, popup.menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.edit_comment -> {
                        listener.onClickEdit(data, true)
                    }
                    R.id.remove_comment -> {
                        listener.onClickDelete(data, adapterPosition)
                    }
                }
                true
            }
            popup.show()
        }
    }
}