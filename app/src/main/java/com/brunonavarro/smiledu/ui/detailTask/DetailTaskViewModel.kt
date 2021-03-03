package com.brunonavarro.smiledu.ui.detailTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.data.repository.detailTaskRepository.DetailTaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTaskViewModel(
    private var detailTaskRepository: DetailTaskRepository
): ViewModel() {

    var task = MutableLiveData<Task>()
    var comments = MutableLiveData<MutableList<Comment>>()

    var listener: DetailTaskListener? = null

    var taskId: Int? = null

    fun getTask(){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            task.postValue(detailTaskRepository.getTask(taskId!!))
            listener?.showProgressBar(false)
        }
    }

    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            detailTaskRepository.removeTask(task)
            listener?.showProgressBar(false)
            listener?.createTaskSuccess()
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            detailTaskRepository.updateTask(task)
            listener?.showProgressBar(false)
        }
    }

    fun getComments(){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            comments.postValue(detailTaskRepository.getComments(taskId!!).toMutableList())
            listener?.showProgressBar(false)
        }
    }

    fun addComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            detailTaskRepository.addComment(comment)
            listener?.showProgressBar(false)
            listener?.createTaskSuccess()
        }
    }

    fun deleteComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            detailTaskRepository.removeComment(comment)
            listener?.showProgressBar(false)
            listener?.createTaskSuccess()
        }
    }

    fun updateComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            detailTaskRepository.updateComment(comment)
            listener?.showProgressBar(false)
        }
    }
}