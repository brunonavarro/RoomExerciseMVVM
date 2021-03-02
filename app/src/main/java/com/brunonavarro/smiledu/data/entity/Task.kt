package com.brunonavarro.smiledu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var title: String? = null,
    var body: String? = null,
    var isComplete: Boolean? = null,
    var createDate: String? = null,
    var finishDate: String? = null
)