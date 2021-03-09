package com.brunonavarro.smiledu.ui.adapters.main.holder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ItemTaskBinding
import com.brunonavarro.smiledu.ui.adapters.main.adapter.TaskListener
import com.brunonavarro.smiledu.ui.detailTask.DetailTaskActivity

class TaskHolder(
    val binding: ItemTaskBinding,
    val listener: TaskListener
) : RecyclerView.ViewHolder(binding.root){

    var context: Context? = null

    fun bind(data: Task){
        context = binding.root.context
        binding.task = data

        val orderNumber = data.id ?: 0

        binding.orderNumber.text = if (orderNumber >= 10) orderNumber.toString() else "0$orderNumber"

        binding.isCompleteCheckBox.setChecked(data.isComplete)

        binding.isCompleteCheckBox.setOnClickListener {
            if (data.isComplete){
                binding.isCompleteCheckBox.isChecked = false
                listener.onIsCompleteTask(data, false)
            }else{
                binding.isCompleteCheckBox.isChecked = true
                listener.onIsCompleteTask(data, true)
            }
        }

        binding.root.setOnClickListener {
            openDetailTask(data)
        }
    }

    private fun openDetailTask(data: Task) {
        val intent = Intent(context, DetailTaskActivity::class.java)
        intent.putExtra("id", data.id)
        context?.startActivity(intent)
    }
}