package com.brunonavarro.smiledu

import android.app.Application
import android.content.Context
import androidx.room.RoomDatabase
import com.brunonavarro.smiledu.data.database.AppDatabase
import com.brunonavarro.smiledu.data.repository.taskRepository.TaskRepository
import com.brunonavarro.smiledu.data.repository.taskRepository.dao.TaskDAO
import com.brunonavarro.smiledu.ui.main.MainViewModelFactory
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

        val rooDatabase = AppDatabase.getInstance(applicationContext)

        bind() from singleton { rooDatabase }

        bind() from singleton { TaskRepository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }
}