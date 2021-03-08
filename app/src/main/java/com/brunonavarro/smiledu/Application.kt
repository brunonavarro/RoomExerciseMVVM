package com.brunonavarro.smiledu

import android.app.Application
import com.brunonavarro.shared.AppDatabase
import com.brunonavarro.shared.DatabaseDriverFactory
import com.brunonavarro.shared.repository.detailRepository.DetailTaskRepository
import com.brunonavarro.shared.repository.taskRepository.TaskRepository
import com.brunonavarro.smiledu.viewModel.detailTask.DetailTaskViewModelFactory
import com.brunonavarro.smiledu.viewModel.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Application: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        val sqlDriver = DatabaseDriverFactory(applicationContext)
        bind() from singleton { sqlDriver }
        val database = AppDatabase(sqlDriver.createDriver())

        bind() from singleton { database.appDatabaseQueries }

        bind() from singleton { TaskRepository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }

        bind() from singleton { DetailTaskRepository(instance()) }
        bind() from provider { DetailTaskViewModelFactory(instance()) }
    }
}