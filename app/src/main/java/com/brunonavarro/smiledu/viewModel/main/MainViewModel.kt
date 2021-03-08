package com.brunonavarro.smiledu.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.shared.repository.taskRepository.TaskRepository
import com.brunonavarro.smiledu.data.entity.Task
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
            val taskValue = mutableListOf<Task>()
            taskRepository.getTasks().toMutableList().forEach {
                taskValue.add(Task(it.id, it.title, it.body,
                    it.isComplete != null && it.isComplete != 0, it.createDate, it.finishDate))
            }
            taskList.postValue(taskValue)
            mainListener?.showProgressBar(false)
        }
    }

    fun addTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = if(task.isComplete){ 1 }else{ 0 }
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            taskRepository.addTask(taskValue)
            mainListener?.showProgressBar(false)
            mainListener?.createTaskSuccess()
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = if(task.isComplete) 1 else 0
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            taskRepository.updateTask(taskValue)
            mainListener?.showProgressBar(false)
        }
    }

    fun updateIsCompleteTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = if(task.isComplete) 1 else 0
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            taskRepository.updateIsComplete(taskValue)
            mainListener?.showProgressBar(false)
        }
    }
}