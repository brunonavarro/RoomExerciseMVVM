package com.brunonavarro.smiledu.data.repository.taskRepository.dao

import androidx.room.*
import com.brunonavarro.smiledu.data.entity.Task

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTask(id: Int): Task
}