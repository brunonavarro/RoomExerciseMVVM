package com.brunonavarro.smiledu.viewModel.detailTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunonavarro.shared.repository.detailRepository.DetailTaskRepository

class DetailTaskViewModelFactory(
    private var detailTaskRepository: DetailTaskRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailTaskViewModel(detailTaskRepository) as T
    }
}