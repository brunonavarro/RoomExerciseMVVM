package com.brunonavarro.smiledu.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ItemTaskBinding

class TaskAdapter(var listener: TaskListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList = mutableListOf<Task>()

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
        val item = itemList[position]
        val itemHolder = holder as TaskHolder
        itemHolder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}