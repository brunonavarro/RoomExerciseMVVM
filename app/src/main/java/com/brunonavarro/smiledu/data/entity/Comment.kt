package com.brunonavarro.smiledu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comment", foreignKeys = [ForeignKey(entity = Task::class,
        parentColumns = ["id"], childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE)])
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val taskId: Int? = null,
    var message: String? = null
)