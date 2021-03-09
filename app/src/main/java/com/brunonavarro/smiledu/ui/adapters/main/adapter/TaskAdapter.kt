package com.brunonavarro.smiledu.ui.adapters.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ItemTaskBinding
import com.brunonavarro.smiledu.ui.adapters.main.holder.TaskHolder

class TaskAdapter(var listener: TaskListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList = mutableListOf<Task>()

    fun addItem(position: Int, task: Task){
        itemList.add(task)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, task: Task){
        itemList.remove(task)
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int, task: Task){
        itemList.forEach {
            if (it.id == task.id){
                it.id = task.id
                it.isComplete = task.isComplete
                it.title = task.title
                it.body = task.body
                it.createDate = task.createDate
                it.finishDate = task.finishDate
            }
        }
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskHolder(
                ItemTaskBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ),
                listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        itemList.sortByDescending { it.id }
        val item = itemList[position]
        val itemHolder = holder as TaskHolder
        itemHolder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}