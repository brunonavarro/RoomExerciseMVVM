package com.brunonavarro.smiledu.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.data.repository.taskRepository.TaskRepository
import com.brunonavarro.smiledu.util.Constants.ERROR_GET_TASK_LIST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private var taskRepository: TaskRepository
): ViewModel() {

    var taskList = MutableLiveData<MutableList<Task>>()

    var taskListComplete = MutableLiveData<MutableList<Task>>()
    var taskListInComplete = MutableLiveData<MutableList<Task>>()

    var mainListener: MainListener? = null

    init {
        getTasks()
    }

    fun getTasks(){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val result = taskRepository.getTasks()
            if (result.success && !result.taskList.isNullOrEmpty()){
                taskList.postValue(result.taskList)
                mainListener?.showProgressBar(false)
            }else{
                mainListener?.showProgressBar(false)
                mainListener?.errorMessage(ERROR_GET_TASK_LIST, null)
            }
        }
    }

    fun addTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val result = taskRepository.addTask(task)
            if (result.success && !result.taskList.isNullOrEmpty()){
                taskList.postValue(result.taskList)
                mainListener?.showProgressBar(false)
                mainListener?.createTaskSuccess()
            }else{
                mainListener?.showProgressBar(false)
                mainListener?.errorMessage(ERROR_GET_TASK_LIST, null)
            }
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val result = taskRepository.updateTask(task)
            if (result.success && !result.taskList.isNullOrEmpty()){
                taskList.postValue(result.taskList)
                mainListener?.showProgressBar(false)
                mainListener?.createTaskSuccess()
            }else{
                mainListener?.showProgressBar(false)
                mainListener?.errorMessage(ERROR_GET_TASK_LIST, null)
            }
        }
    }
}