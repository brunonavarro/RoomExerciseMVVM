package com.brunonavarro.shared.viewModel.main

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.publish.PublishSubject
import com.brunonavarro.shared.model.Task
import com.brunonavarro.shared.repository.taskRepository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private var taskRepository: TaskRepository
) {

    private var _taskList = PublishSubject<MutableList<Task>>()
    var taskList: Observable<MutableList<Task>> = _taskList
    private val _maxTask = PublishSubject<Task>()
    var maxTask: Observable<Task> = _maxTask

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
                    it.isComplete ?: false, it.createDate, it.finishDate))
            }
            _taskList.onNext(taskValue)
            mainListener?.showProgressBar(false)
        }
    }

    fun getMaxTask(){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val taskValue = Task()
            taskRepository.getMaxTask().let {
                taskValue.id = it.id
                taskValue.title = it.title
                taskValue.body = it.body
                taskValue.isComplete = it.isComplete ?: false
                taskValue.createDate = it.createDate
                taskValue.finishDate = it.finishDate
            }
            _maxTask.onNext(taskValue)
            mainListener?.createTaskSuccess(taskValue)
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
            taskValue.isComplete = task.isComplete ?: false
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            taskRepository.addTask(taskValue)
            mainListener?.showProgressBar(false)
        }
    }

    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.Main).launch {
            mainListener?.showProgressBar(true)
            val taskValue = com.brunonavarro.shared.model.Task()
            taskValue.id = task.id
            taskValue.body = task.body
            taskValue.title = task.title
            taskValue.isComplete = task.isComplete
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
            taskValue.isComplete = task.isComplete
            taskValue.createDate = task.createDate
            taskValue.finishDate = task.finishDate
            taskRepository.updateIsComplete(taskValue)
            mainListener?.showProgressBar(false)
        }
    }
}