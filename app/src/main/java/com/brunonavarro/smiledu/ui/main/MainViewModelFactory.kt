package com.brunonavarro.smiledu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunonavarro.smiledu.data.repository.taskRepository.TaskRepository

class MainViewModelFactory(
    private var taskRepository: TaskRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(taskRepository) as T
    }
}