package com.brunonavarro.smiledu.data.repository.detailTaskRepository.dao

import androidx.room.*
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CommentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Update
    suspend fun updateComment(comment: Comment)

    @Delete
    suspend fun deleteComment(comment: Comment)

    @Query("SELECT * FROM comment WHERE taskId = :taskId")
    suspend fun getComments(taskId: Int): List<Comment>

    @Query("SELECT * FROM comment WHERE id = :id")
    suspend fun getComment(id: Int): Comment
}