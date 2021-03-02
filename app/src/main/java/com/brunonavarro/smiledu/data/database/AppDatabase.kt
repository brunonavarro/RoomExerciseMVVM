package com.brunonavarro.smiledu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.data.repository.taskRepository.dao.TaskDAO
import org.kodein.di.KodeinProperty

@Database(entities = [Task::class, Comment::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDAO

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "database-name"
                ).build()
            }
            return INSTANCE!!
        }
    }

}