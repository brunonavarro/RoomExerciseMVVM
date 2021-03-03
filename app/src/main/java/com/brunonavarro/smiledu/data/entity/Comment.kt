package com.brunonavarro.smiledu.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comment", foreignKeys = [ForeignKey(entity = Task::class,
        parentColumns = ["id"], childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE)])
data class Comment(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null,
    @ForeignKey(
        entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE)
    @ColumnInfo(name = "taskId") var taskId: Int? = null,
    @ColumnInfo(name = "message") var message: String? = null
)