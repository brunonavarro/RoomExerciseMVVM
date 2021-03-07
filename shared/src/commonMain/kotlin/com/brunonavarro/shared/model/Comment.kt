package com.brunonavarro.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Comment(
    var id: Int? = null,
    var taskId: Int? = null,
    var message: String? = null
)