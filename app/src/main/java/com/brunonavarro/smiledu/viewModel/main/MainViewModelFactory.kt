package com.brunonavarro.smiledu.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunonavarro.shared.repository.taskRepository.TaskRepository

class MainViewModelFactory(
    private var taskRepository: TaskRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(taskRepository) as T
    }
}