package com.brunonavarro.shared.viewModel.detailTask


import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.publish.PublishSubject
import com.brunonavarro.shared.model.Comment
import com.brunonavarro.shared.model.Task
import com.brunonavarro.shared.repository.detailRepository.DetailTaskRepository
import com.brunonavarro.shared.util.Constants

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTaskViewModel(
    private var detailTaskRepository: DetailTaskRepository
) {

    private var _task = PublishSubject<Task>()
    var task: Observable<Task> = _task
    private var _comments = PublishSubject<MutableList<Comment>>()
    var comments: Observable<MutableList<Comment>> = _comments
    private var _maxComment = PublishSubject<Comment>()
    var maxComment: Observable<Comment> = _maxComment

    var listener: DetailTaskListener? = null

    var taskId: Int? = null

    fun setComments(comments: MutableList<Comment>){
        _comments.onNext(comments)
    }

    fun setMaxComment(comment: Comment){
        _maxComment.onNext(comment)
    }

    fun setTask(task: Task){
        _task.onNext(task)
    }

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

            _task.onNext(taskData)
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
            _maxComment.onNext(commentValues)
            listener?.messageSuccess(Constants.SUCCESS_ADD_COMMENT, commentValues)
            listener?.showProgressBar(false)
        }
    }

    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val taskValue = Task()
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
            val taskValue = Task()
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
            _comments.onNext(commentValues)
            listener?.showProgressBar(false)
        }
    }

    fun addComment(comment: Comment){
        CoroutineScope(Dispatchers.Main).launch {
            listener?.showProgressBar(true)
            val commentValue = Comment()
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
            val commentValue = Comment()
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
            val commentValue = Comment()
            commentValue.id = comment.id
            commentValue.taskId = comment.taskId
            commentValue.message = comment.message
            detailTaskRepository.updateComment(commentValue)
            listener?.messageSuccess(Constants.SUCCESS_UPDATE_COMMENT, null)
            listener?.showProgressBar(false)
        }
    }
}