package com.brunonavarro.smiledu.viewModel.detailTask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunonavarro.shared.repository.detailRepository.DetailTaskRepository
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.ui.detailTask.DetailTaskListener
import com.brunonavarro.smiledu.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTaskViewModel(
    private var detailTaskRepository: DetailTaskRepository
): ViewModel() {

    var task = MutableLiveData<Task>()
    var comments = MutableLiveData<MutableList<Comment>>()
    var maxComment = MutableLiveData<Comment>()

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
                taskData.isComplete = it.isComplete ?: false
                taskData.createDate = it.createDate
                taskData.finishDate = it.finishDate
            }

            task.postValue(taskData)
            listener?.showProgressBar(false)
        }
    }

    fun getMaxComment(){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValues = Comment()
            detailTaskRepository.getMaxComment().let {
                commentValues.id = it.id
                commentValues.taskId = it.taskId
                commentValues.message = it.message
            }
            maxComment.postValue(commentValues)
            listener?.messageSuccess(Constants.SUCCESS_ADD_COMMENT, commentValues)
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
            listener?.messageSuccess(Constants.SUCCESS_DELETE_TASK, null)
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
            listener?.messageSuccess(Constants.SUCCESS_UPDATE_TASK, null)
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
            listener?.messageSuccess(Constants.SUCCESS_ADD_COMMENT, null)
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
            listener?.messageSuccess(Constants.SUCCESS_DELETE_COMMENT, null)
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
            listener?.messageSuccess(Constants.SUCCESS_UPDATE_COMMENT, null)
            listener?.showProgressBar(false)
        }
    }
}