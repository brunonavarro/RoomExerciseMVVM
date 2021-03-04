package com.brunonavarro.smiledu.viewModel.detailTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunonavarro.smiledu.data.repository.detailTaskRepository.DetailTaskRepository

class DetailTaskViewModelFactory(
    private var detailTaskRepository: DetailTaskRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailTaskViewModel(detailTaskRepository) as T
    }
}