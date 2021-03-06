package com.brunonavarro.smiledu.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.data.repository.taskRepository.TaskRepository
import com.brunonavarro.smiledu.ui.main.MainListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private var taskRepository: TaskRepository
): ViewModel() {

    var taskList = MutableLiveData<MutableList<Task>>()

    var mainListener: MainListener? = null

    init {
        getTasks()
    }

    fun getTasks(){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            taskList.postValue(taskRepository.getTasks().toMutableList())
            mainListener?.showProgressBar(false)
        }
    }

    fun addTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            taskRepository.addTask(task)
            mainListener?.showProgressBar(false)
            mainListener?.createTaskSuccess()
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            taskRepository.updateTask(task)
            mainListener?.showProgressBar(false)
        }
    }
}