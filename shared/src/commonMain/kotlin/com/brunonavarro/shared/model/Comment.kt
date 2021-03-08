package com.brunonavarro.shared.model

data class Comment(
    var id: Int? = null,
    var taskId: Int? = null,
    var message: String? = null
)