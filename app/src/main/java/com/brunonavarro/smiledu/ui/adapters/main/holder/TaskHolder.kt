package com.brunonavarro.smiledu.ui.adapters.main.holder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.brunonavarro.shared.model.Task
import com.brunonavarro.smiledu.R
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

        val orderNumber = data.id ?: 0

        binding.orderNumber.text = if (orderNumber >= 10) orderNumber.toString() else "0$orderNumber"
        binding.activityTitle.text = data.title
        binding.activityBody.text = data.body
        binding.activityCreate.text = context?.getString(R.string.activity_create_value, data.createDate)
        binding.activityFinish.text = context?.getString(R.string.activity_finish_value, data.finishDate)

        val isComplete = data.isComplete ?: false

        binding.isCompleteCheckBox.setChecked(isComplete)

        binding.isCompleteCheckBox.setOnClickListener {
            if (isComplete){
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
        intent.putExtra("id", data.id ?: 0)
        context?.startActivity(intent)
    }
}