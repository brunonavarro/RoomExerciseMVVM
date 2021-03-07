package com.brunonavarro.smiledu.viewModel.detailTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.shared.repository.detailRepository.DetailTaskRepository
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.ui.detailTask.DetailTaskListener
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
            val taskData = Task()
            detailTaskRepository.getTask(taskId!!).let {
                taskData.id = it.id
                taskData.title = it.title
                taskData.body = it.body
                taskData.isComplete = it.isComplete
                taskData.createDate = it.createDate
                taskData.finishDate = it.finishDate
            }

            task.postValue(taskData)
            listener?.showProgressBar(false)
        }
    }

    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = task.isComplete
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            detailTaskRepository.removeTask(taskValue)
            listener?.showProgressBar(false)
            listener?.createTaskSuccess()
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = task.isComplete
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            detailTaskRepository.updateTask(taskValue)
            listener?.showProgressBar(false)
        }
    }

    fun getComments(){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValues = mutableListOf<Comment>()
            detailTaskRepository.getComments(taskId!!).toMutableList().forEach {
                commentValues.add(Comment(it.id, it.taskId, it.message))
            }
            comments.postValue(commentValues)
            listener?.showProgressBar(false)
        }
    }

    fun addComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValue = com.brunonavarro.shared.model.Comment()
            commentValue.id = comment.id
            commentValue.taskId = comment.taskId
            commentValue.message = comment.message
            detailTaskRepository.addComment(commentValue)
            listener?.showProgressBar(false)
            listener?.createTaskSuccess()
        }
    }

    fun deleteComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValue = com.brunonavarro.shared.model.Comment()
            commentValue.id = comment.id
            commentValue.taskId = comment.taskId
            commentValue.message = comment.message
            detailTaskRepository.removeComment(commentValue)
            listener?.showProgressBar(false)
        }
    }

    fun updateComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValue = com.brunonavarro.shared.model.Comment()
            commentValue.id = comment.id
            commentValue.taskId = comment.taskId
            commentValue.message = comment.message
            detailTaskRepository.updateComment(commentValue)
            listener?.showProgressBar(false)
        }
    }
}