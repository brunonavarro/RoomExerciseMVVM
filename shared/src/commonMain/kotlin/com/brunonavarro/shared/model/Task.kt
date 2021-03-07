package com.brunonavarro.shared.model

data class Task(
    var id: Int? = null,
    var title: String? = null,
    var body: String? = null,
    var isComplete: Boolean = false,
    var createDate: String? = null,
    var finishDate: String? = null
)