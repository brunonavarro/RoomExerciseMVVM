package com.brunonavarro.smiledu.ui.main.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskHolder(
    val binding: ItemTaskBinding,
    val listener: TaskListener
) : RecyclerView.ViewHolder(binding.root){

    var context: Context? = null

    fun bind(data: Task){
        context = binding.root.context

        val orderNumber = (data.id ?: adapterPosition)

        binding.activityTitle.text = data.title
        binding.orderNumber.text = if (orderNumber >= 10) orderNumber.toString() else "0$orderNumber"
        binding.activityBody.text = data.body.toString()

        binding.activityCreate.text = data.createDate.toString()
        binding.activityFinish.text = data.finishDate.toString()

        binding.isCompleteCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            listener.onIsCompleteTask(data, b)
        }

    }
}