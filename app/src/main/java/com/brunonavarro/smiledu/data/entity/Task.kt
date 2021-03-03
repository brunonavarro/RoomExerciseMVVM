package com.brunonavarro.smiledu.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "body") var body: String? = null,
    @ColumnInfo(name = "isComplete", defaultValue = "false") var isComplete: Boolean = false,
    @ColumnInfo(name = "createDate") var createDate: String? = null,
    @ColumnInfo(name = "finishDate") var finishDate: String? = null
)